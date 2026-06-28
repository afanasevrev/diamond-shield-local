package ru.diamondshield.local.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.diamondshield.local.config.LocalServerProperties;
import ru.diamondshield.local.dto.central.CentralConfigResponse;
import ru.diamondshield.local.dto.central.CentralHeartbeatRequest;

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

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));

        headers.set("X-Local-Server-Id", properties.getLocalServer().getId().toString());
        headers.set("X-Local-Server-Token", properties.getLocalServer().getToken());

        return headers;
    }
}