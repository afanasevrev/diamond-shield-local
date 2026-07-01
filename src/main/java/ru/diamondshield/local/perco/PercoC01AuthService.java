package ru.diamondshield.local.perco;

import org.springframework.stereotype.Service;
import ru.diamondshield.local.config.LocalServerProperties;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class PercoC01AuthService {

    private final LocalServerProperties properties;

    public PercoC01AuthService(LocalServerProperties properties) {
        this.properties = properties;
    }

    public String buildAuthHash(String salt) {
        String password = properties.getPerco().getPassword();

        if (password == null) {
            password = "";
        }

        return md5(salt + password);
    }

    private String md5(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));

            StringBuilder builder = new StringBuilder();

            for (byte b : bytes) {
                builder.append(String.format("%02x", b));
            }

            return builder.toString();
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot calculate MD5", ex);
        }
    }
}