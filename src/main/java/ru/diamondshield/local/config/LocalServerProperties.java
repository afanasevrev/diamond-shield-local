package ru.diamondshield.local.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.UUID;

@ConfigurationProperties(prefix = "diamondshield")
public class LocalServerProperties {

    private LocalServer localServer = new LocalServer();
    private Central central = new Central();
    private Sync sync = new Sync();
    private Perco perco = new Perco();

    public LocalServerProperties() {
    }

    public LocalServer getLocalServer() {
        return localServer;
    }

    public void setLocalServer(LocalServer localServer) {
        this.localServer = localServer;
    }

    public Central getCentral() {
        return central;
    }

    public void setCentral(Central central) {
        this.central = central;
    }

    public Sync getSync() {
        return sync;
    }

    public void setSync(Sync sync) {
        this.sync = sync;
    }

    public Perco getPerco() {
        return perco;
    }

    public void setPerco(Perco perco) {
        this.perco = perco;
    }

    public static class LocalServer {

        private UUID id;
        private String token;

        public LocalServer() {
        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public static class Central {

        private String baseUrl;
        private int connectTimeoutMs;
        private int readTimeoutMs;

        public Central() {
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public int getConnectTimeoutMs() {
            return connectTimeoutMs;
        }

        public void setConnectTimeoutMs(int connectTimeoutMs) {
            this.connectTimeoutMs = connectTimeoutMs;
        }

        public int getReadTimeoutMs() {
            return readTimeoutMs;
        }

        public void setReadTimeoutMs(int readTimeoutMs) {
            this.readTimeoutMs = readTimeoutMs;
        }
    }

    public static class Sync {

        private long heartbeatIntervalMs;
        private long configPullIntervalMs;
        private long eventPushIntervalMs;
        private long devicePollIntervalMs;

        public Sync() {
        }

        public long getHeartbeatIntervalMs() {
            return heartbeatIntervalMs;
        }

        public void setHeartbeatIntervalMs(long heartbeatIntervalMs) {
            this.heartbeatIntervalMs = heartbeatIntervalMs;
        }

        public long getConfigPullIntervalMs() {
            return configPullIntervalMs;
        }

        public void setConfigPullIntervalMs(long configPullIntervalMs) {
            this.configPullIntervalMs = configPullIntervalMs;
        }

        public long getEventPushIntervalMs() {
            return eventPushIntervalMs;
        }

        public void setEventPushIntervalMs(long eventPushIntervalMs) {
            this.eventPushIntervalMs = eventPushIntervalMs;
        }

        public long getDevicePollIntervalMs() {
            return devicePollIntervalMs;
        }

        public void setDevicePollIntervalMs(long devicePollIntervalMs) {
            this.devicePollIntervalMs = devicePollIntervalMs;
        }
    }

    public static class Perco {

        private boolean enabled;
        private String host;
        private int port;
        private int timeoutMs;

        public Perco() {
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getTimeoutMs() {
            return timeoutMs;
        }

        public void setTimeoutMs(int timeoutMs) {
            this.timeoutMs = timeoutMs;
        }
    }
}