package ru.diamondshield.local.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.diamondshield.local.config.LocalServerProperties;
import ru.diamondshield.local.dto.central.*;

import java.util.List;

@Service
public class CentralClientService {

    private final RestTemplate restTemplate;
    private final LocalServerProperties properties;

    public CentralClientService(RestTemplate restTemplate,
                                LocalServerProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public void heartbeat(CentralHeartbeatRequest request) {
        String url = properties.getCentral().getBaseUrl() + "/api/local-sync/heartbeat";

        HttpEntity<CentralHeartbeatRequest> entity = new HttpEntity<>(request, headers());

        restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );
    }

    public CentralConfigResponse getConfig() {
        String url = properties.getCentral().getBaseUrl() + "/api/local-sync/config";

        HttpEntity<Void> entity = new HttpEntity<>(headers());

        ResponseEntity<CentralConfigResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                CentralConfigResponse.class
        );

        return response.getBody();
    }

    public CentralBatchResponse pushAccessEvents(List<CentralAccessEventPushRequest> events) {
        String url = properties.getCentral().getBaseUrl() + "/api/local-sync/access-events";

        HttpEntity<List<CentralAccessEventPushRequest>> entity =
                new HttpEntity<>(events, headers());

        ResponseEntity<CentralBatchResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                CentralBatchResponse.class
        );

        return response.getBody();
    }

    public CentralBatchResponse pushAlarmEvents(List<CentralAlarmEventPushRequest> events) {
        String url = properties.getCentral().getBaseUrl() + "/api/local-sync/alarm-events";

        HttpEntity<List<CentralAlarmEventPushRequest>> entity =
                new HttpEntity<>(events, headers());

        ResponseEntity<CentralBatchResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                CentralBatchResponse.class
        );

        return response.getBody();
    }

    public CentralBatchResponse pushDeviceStatuses(List<CentralDeviceStatusPushRequest> statuses) {
        String url = properties.getCentral().getBaseUrl() + "/api/local-sync/device-statuses";

        HttpEntity<List<CentralDeviceStatusPushRequest>> entity =
                new HttpEntity<>(statuses, headers());

        ResponseEntity<CentralBatchResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                CentralBatchResponse.class
        );

        return response.getBody();
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        headers.set("X-Local-Server-Id", properties.getLocalServer().getId().toString());
        headers.set("X-Local-Server-Token", properties.getLocalServer().getToken());

        return headers;
    }
}
