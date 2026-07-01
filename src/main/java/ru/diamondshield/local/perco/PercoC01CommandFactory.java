package ru.diamondshield.local.perco;

import org.springframework.stereotype.Component;
import ru.diamondshield.local.config.LocalServerProperties;
import tools.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class PercoC01CommandFactory {

    private final ObjectMapper objectMapper;
    private final LocalServerProperties properties;

    public PercoC01CommandFactory(ObjectMapper objectMapper,
                                  LocalServerProperties properties) {
        this.objectMapper = objectMapper;
        this.properties = properties;
    }

    public String auth(String hash) {
        Map<String, Object> root = new LinkedHashMap<>();
        Map<String, Object> auth = new LinkedHashMap<>();

        root.put("set", "auth");
        auth.put("hash", hash);
        root.put("auth", auth);

        return toJson(root);
    }

    public String getState() {
        Map<String, Object> root = new LinkedHashMap<>();
        root.put("get", "state");
        return toJson(root);
    }

    public String getNet() {
        Map<String, Object> root = new LinkedHashMap<>();
        root.put("get", "net");
        return toJson(root);
    }

    public String getReader(Integer number) {
        Map<String, Object> root = new LinkedHashMap<>();
        root.put("get", "reader");

        if (number != null) {
            root.put("number", number);
        }

        return toJson(root);
    }

    public String getExdev(Integer number) {
        Map<String, Object> root = new LinkedHashMap<>();
        root.put("get", "exdev");

        if (number != null) {
            root.put("number", number);
        }

        return toJson(root);
    }

    public String openExdev(Integer number,
                            Integer direction) {
        return openExdev(
                number,
                direction,
                properties.getPerco().getDefaultOpenType(),
                properties.getPerco().getDefaultOpenTimeMs()
        );
    }

    public String openExdev(Integer number,
                            Integer direction,
                            String openType,
                            Integer openTime) {
        Map<String, Object> root = new LinkedHashMap<>();
        Map<String, Object> exdev = new LinkedHashMap<>();

        root.put("control", "exdev");

        exdev.put("number", number);
        exdev.put("direction", direction);
        exdev.put("action", "open");
        exdev.put("open_type", openType == null ? "open once" : openType);
        exdev.put("open_time", openTime == null ? 1000 : openTime);

        root.put("exdev", exdev);

        return toJson(root);
    }

    public String closeExdev(Integer number,
                             Integer direction) {
        Map<String, Object> root = new LinkedHashMap<>();
        Map<String, Object> exdev = new LinkedHashMap<>();

        root.put("control", "exdev");

        exdev.put("number", number);
        exdev.put("direction", direction);
        exdev.put("action", "close");
        exdev.put("open_type", "");
        exdev.put("open_time", properties.getPerco().getDefaultOpenTimeMs());

        root.put("exdev", exdev);

        return toJson(root);
    }

    public String banAccess(Integer number,
                            Integer direction) {
        Map<String, Object> root = new LinkedHashMap<>();
        Map<String, Object> access = new LinkedHashMap<>();

        root.put("control", "access");

        access.put("number", number);
        access.put("direction", direction);

        root.put("access", access);

        return toJson(root);
    }

    public String setAccessMode(Integer number,
                                Integer direction,
                                String accessMode) {
        Map<String, Object> root = new LinkedHashMap<>();
        Map<String, Object> acm = new LinkedHashMap<>();

        root.put("control", "acm");

        acm.put("number", number);
        acm.put("direction", direction);
        acm.put("access_mode", accessMode);

        root.put("acm", acm);

        return toJson(root);
    }

    private String toJson(Map<String, Object> value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot serialize PERCo command", ex);
        }
    }
}