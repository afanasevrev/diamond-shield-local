package ru.diamondshield.local.service;

import org.springframework.stereotype.Service;
import ru.diamondshield.local.dto.central.CentralConfigResponse;
import ru.diamondshield.local.dto.central.CentralHeartbeatRequest;

import java.net.InetAddress;

@Service
public class CentralSyncService {

    private final CentralClientService.CentralClientService centralClientService;
    private final LocalConfigApplyService localConfigApplyService;

    public CentralSyncService(CentralClientService.CentralClientService centralClientService,
                              LocalConfigApplyService localConfigApplyService) {
        this.centralClientService = centralClientService;
        this.localConfigApplyService = localConfigApplyService;
    }

    public void heartbeat() {
        CentralHeartbeatRequest request = new CentralHeartbeatRequest();

        request.setIpAddress(detectIpAddress());
        request.setSoftwareVersion("local-server-mvp-1.0.0");
        request.setStatus("online");
        request.setMessage("OK");

        centralClientService.heartbeat(request);
    }

    public void pullConfig() {
        CentralConfigResponse config = centralClientService.getConfig();
        localConfigApplyService.apply(config);
    }

    private String detectIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception ex) {
            return "127.0.0.1";
        }
    }
}