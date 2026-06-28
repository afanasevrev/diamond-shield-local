package ru.diamondshield.local.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.diamondshield.local.config.LocalServerProperties;
import ru.diamondshield.local.dto.localcheck.LocalAccessCheckRequest;
import ru.diamondshield.local.dto.localcheck.LocalAccessCheckResponse;
import ru.diamondshield.local.entity.*;
import ru.diamondshield.local.repository.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocalAccessCheckService {

    private final LocalConfigStateRepository configStateRepository;
    private final LocalAccessPointRepository accessPointRepository;
    private final LocalAccessIdentifierRepository identifierRepository;
    private final LocalPersonRepository personRepository;
    private final LocalAccessRuleRepository ruleRepository;
    private final LocalAccessEventRepository eventRepository;
    private final IdentifierSecurityService identifierSecurityService;
    private final LocalScheduleCheckService scheduleCheckService;
    private final LocalServerProperties properties;

    public LocalAccessCheckService(LocalConfigStateRepository configStateRepository,
                                   LocalAccessPointRepository accessPointRepository,
                                   LocalAccessIdentifierRepository identifierRepository,
                                   LocalPersonRepository personRepository,
                                   LocalAccessRuleRepository ruleRepository,
                                   LocalAccessEventRepository eventRepository,
                                   IdentifierSecurityService identifierSecurityService,
                                   LocalScheduleCheckService scheduleCheckService,
                                   LocalServerProperties properties) {
        this.configStateRepository = configStateRepository;
        this.accessPointRepository = accessPointRepository;
        this.identifierRepository = identifierRepository;
        this.personRepository = personRepository;
        this.ruleRepository = ruleRepository;
        this.eventRepository = eventRepository;
        this.identifierSecurityService = identifierSecurityService;
        this.scheduleCheckService = scheduleCheckService;
        this.properties = properties;
    }

    @Transactional
    public LocalAccessCheckResponse check(LocalAccessCheckRequest request) {
        LocalDateTime checkedAt = request.getEventTime() == null
                ? LocalDateTime.now()
                : request.getEventTime();

        UUID objectId = getCurrentObjectId();

        LocalAccessPoint accessPoint = accessPointRepository
                .findById(request.getAccessPointId())
                .orElse(null);

        if (accessPoint == null) {
            LocalAccessEvent event = saveEvent(
                    objectId,
                    request.getAccessPointId(),
                    request.getReaderId(),
                    request.getControllerId(),
                    null,
                    null,
                    checkedAt,
                    request.getDirection(),
                    "denied",
                    "Access point not found",
                    request.getIdentifierType(),
                    maskFromRequest(request),
                    null,
                    true
            );

            return response(
                    "access_point_not_found",
                    false,
                    "Access point not found",
                    null,
                    null,
                    event.getId(),
                    checkedAt
            );
        }

        if (!Boolean.TRUE.equals(accessPoint.getActive())) {
            LocalAccessEvent event = saveEvent(
                    objectId,
                    request.getAccessPointId(),
                    request.getReaderId(),
                    request.getControllerId(),
                    null,
                    null,
                    checkedAt,
                    request.getDirection(),
                    "denied",
                    "Access point is inactive",
                    request.getIdentifierType(),
                    maskFromRequest(request),
                    null,
                    true
            );

            return response(
                    "access_point_inactive",
                    false,
                    "Access point is inactive",
                    null,
                    null,
                    event.getId(),
                    checkedAt
            );
        }

        String normalizedIdentifier = identifierSecurityService.normalize(request.getIdentifierValue());
        String identifierHash = identifierSecurityService.sha256(normalizedIdentifier);
        String maskedIdentifier = identifierSecurityService.mask(normalizedIdentifier);

        Optional<LocalAccessIdentifier> identifierOptional =
                identifierRepository.findByIdentifierTypeAndIdentifierValueHash(
                        request.getIdentifierType(),
                        identifierHash
                );

        if (identifierOptional.isEmpty()) {
            LocalAccessEvent event = saveEvent(
                    objectId,
                    request.getAccessPointId(),
                    request.getReaderId(),
                    request.getControllerId(),
                    null,
                    null,
                    checkedAt,
                    request.getDirection(),
                    "unknown",
                    "Unknown identifier",
                    request.getIdentifierType(),
                    maskedIdentifier,
                    identifierHash,
                    true
            );

            return response(
                    "unknown_identifier",
                    false,
                    "Identifier was not found",
                    null,
                    null,
                    event.getId(),
                    checkedAt
            );
        }

        LocalAccessIdentifier identifier = identifierOptional.get();

        LocalPerson person = personRepository
                .findById(identifier.getPersonId())
                .orElse(null);

        if (person == null) {
            LocalAccessEvent event = saveDeniedKnownIdentifier(
                    objectId,
                    request,
                    identifier,
                    checkedAt,
                    "Person not found"
            );

            return response(
                    "person_not_found",
                    false,
                    "Person not found",
                    identifier.getPersonId(),
                    identifier.getId(),
                    event.getId(),
                    checkedAt
            );
        }

        if (!Boolean.TRUE.equals(person.getActive())) {
            LocalAccessEvent event = saveDeniedKnownIdentifier(
                    objectId,
                    request,
                    identifier,
                    checkedAt,
                    "Person is inactive"
            );

            return response(
                    "inactive_person",
                    false,
                    "Person is inactive",
                    person.getId(),
                    identifier.getId(),
                    event.getId(),
                    checkedAt
            );
        }

        if (!"active".equalsIgnoreCase(identifier.getStatus())) {
            LocalAccessEvent event = saveDeniedKnownIdentifier(
                    objectId,
                    request,
                    identifier,
                    checkedAt,
                    "Identifier is not active"
            );

            String decision = "blocked".equalsIgnoreCase(identifier.getStatus())
                    ? "blocked_identifier"
                    : "inactive_identifier";

            return response(
                    decision,
                    false,
                    "Identifier is not active",
                    person.getId(),
                    identifier.getId(),
                    event.getId(),
                    checkedAt
            );
        }

        if (identifier.getValidFrom() != null && checkedAt.isBefore(identifier.getValidFrom())) {
            LocalAccessEvent event = saveDeniedKnownIdentifier(
                    objectId,
                    request,
                    identifier,
                    checkedAt,
                    "Identifier validity period has not started"
            );

            return response(
                    "identifier_not_started",
                    false,
                    "Identifier validity period has not started",
                    person.getId(),
                    identifier.getId(),
                    event.getId(),
                    checkedAt
            );
        }

        if (identifier.getValidTo() != null && checkedAt.isAfter(identifier.getValidTo())) {
            LocalAccessEvent event = saveDeniedKnownIdentifier(
                    objectId,
                    request,
                    identifier,
                    checkedAt,
                    "Identifier validity period has expired"
            );

            return response(
                    "expired_identifier",
                    false,
                    "Identifier validity period has expired",
                    person.getId(),
                    identifier.getId(),
                    event.getId(),
                    checkedAt
            );
        }

        Optional<LocalAccessRule> ruleOptional =
                ruleRepository.findByPersonIdAndAccessPointId(
                        person.getId(),
                        request.getAccessPointId()
                );

        if (ruleOptional.isEmpty()) {
            LocalAccessEvent event = saveDeniedKnownIdentifier(
                    objectId,
                    request,
                    identifier,
                    checkedAt,
                    "No access rule"
            );

            return response(
                    "no_rule",
                    false,
                    "No access rule for this access point",
                    person.getId(),
                    identifier.getId(),
                    event.getId(),
                    checkedAt
            );
        }

        LocalAccessRule rule = ruleOptional.get();

        if (!Boolean.TRUE.equals(rule.getActive())) {
            LocalAccessEvent event = saveDeniedKnownIdentifier(
                    objectId,
                    request,
                    identifier,
                    checkedAt,
                    "Access rule is inactive"
            );

            return response(
                    "inactive_rule",
                    false,
                    "Access rule is inactive",
                    person.getId(),
                    identifier.getId(),
                    event.getId(),
                    checkedAt
            );
        }

        if (rule.getValidFrom() != null && checkedAt.isBefore(rule.getValidFrom())) {
            LocalAccessEvent event = saveDeniedKnownIdentifier(
                    objectId,
                    request,
                    identifier,
                    checkedAt,
                    "Access rule validity period has not started"
            );

            return response(
                    "rule_not_started",
                    false,
                    "Access rule validity period has not started",
                    person.getId(),
                    identifier.getId(),
                    event.getId(),
                    checkedAt
            );
        }

        if (rule.getValidTo() != null && checkedAt.isAfter(rule.getValidTo())) {
            LocalAccessEvent event = saveDeniedKnownIdentifier(
                    objectId,
                    request,
                    identifier,
                    checkedAt,
                    "Access rule validity period has expired"
            );

            return response(
                    "rule_expired",
                    false,
                    "Access rule validity period has expired",
                    person.getId(),
                    identifier.getId(),
                    event.getId(),
                    checkedAt
            );
        }

        boolean scheduleAllowed = scheduleCheckService.isAllowed(rule.getScheduleId(), checkedAt);

        if (!scheduleAllowed) {
            LocalAccessEvent event = saveDeniedKnownIdentifier(
                    objectId,
                    request,
                    identifier,
                    checkedAt,
                    "Out of schedule"
            );

            return response(
                    "out_of_schedule",
                    false,
                    "Access denied by schedule",
                    person.getId(),
                    identifier.getId(),
                    event.getId(),
                    checkedAt
            );
        }

        LocalAccessEvent event = saveEvent(
                objectId,
                request.getAccessPointId(),
                request.getReaderId(),
                request.getControllerId(),
                person.getId(),
                identifier.getId(),
                checkedAt,
                request.getDirection(),
                "allowed",
                "Access granted",
                identifier.getIdentifierType(),
                maskedIdentifier,
                identifier.getIdentifierValueHash(),
                false
        );

        return response(
                "allowed",
                true,
                "Access granted",
                person.getId(),
                identifier.getId(),
                event.getId(),
                checkedAt
        );
    }

    private UUID getCurrentObjectId() {
        return configStateRepository
                .findFirstByLocalServerId(properties.getLocalServer().getId())
                .map(LocalConfigState::getObjectId)
                .orElse(null);
    }

    private String maskFromRequest(LocalAccessCheckRequest request) {
        String normalized = identifierSecurityService.normalize(request.getIdentifierValue());
        return identifierSecurityService.mask(normalized);
    }

    private LocalAccessEvent saveDeniedKnownIdentifier(UUID objectId,
                                                       LocalAccessCheckRequest request,
                                                       LocalAccessIdentifier identifier,
                                                       LocalDateTime checkedAt,
                                                       String reason) {
        return saveEvent(
                objectId,
                request.getAccessPointId(),
                request.getReaderId(),
                request.getControllerId(),
                identifier.getPersonId(),
                identifier.getId(),
                checkedAt,
                request.getDirection(),
                "denied",
                reason,
                identifier.getIdentifierType(),
                null,
                identifier.getIdentifierValueHash(),
                false
        );
    }

    private LocalAccessEvent saveEvent(UUID objectId,
                                       UUID accessPointId,
                                       UUID readerId,
                                       UUID controllerId,
                                       UUID personId,
                                       UUID identifierId,
                                       LocalDateTime eventTime,
                                       String direction,
                                       String accessResult,
                                       String reason,
                                       String identifierType,
                                       String identifierMasked,
                                       String identifierValueHash,
                                       Boolean unknownIdentifier) {
        LocalAccessEvent event = new LocalAccessEvent();

        event.setLocalEventId(UUID.randomUUID().toString());

        event.setObjectId(objectId);
        event.setAccessPointId(accessPointId);
        event.setReaderId(readerId);
        event.setControllerId(controllerId);

        event.setPersonId(personId);
        event.setIdentifierId(identifierId);

        event.setEventTime(eventTime);
        event.setDirection(direction);

        event.setAccessResult(accessResult);
        event.setReason(reason);

        event.setIdentifierType(identifierType);
        event.setIdentifierMasked(identifierMasked);
        event.setIdentifierValueHash(identifierValueHash);

        event.setUnknownIdentifier(Boolean.TRUE.equals(unknownIdentifier));

        event.setSentToCentral(false);

        return eventRepository.save(event);
    }

    private LocalAccessCheckResponse response(String decision,
                                              Boolean allowed,
                                              String reason,
                                              UUID personId,
                                              UUID identifierId,
                                              UUID accessEventId,
                                              LocalDateTime checkedAt) {
        return new LocalAccessCheckResponse(
                decision,
                allowed,
                reason,
                personId,
                identifierId,
                accessEventId,
                checkedAt
        );
    }
}