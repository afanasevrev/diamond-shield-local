package ru.diamondshield.local.service;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class IdentifierSecurityService {

    public String normalize(String value) {
        if (value == null) {
            return null;
        }

        return value.trim().toUpperCase();
    }

    public String sha256(String normalizedValue) {
        if (normalizedValue == null) {
            throw new IllegalArgumentException("Identifier value cannot be null");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(normalizedValue.getBytes(StandardCharsets.UTF_8));

            StringBuilder builder = new StringBuilder();

            for (byte b : hash) {
                builder.append(String.format("%02x", b));
            }

            return builder.toString();
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot calculate identifier hash", ex);
        }
    }

    public String mask(String normalizedValue) {
        if (normalizedValue == null || normalizedValue.isBlank()) {
            return "";
        }

        if (normalizedValue.length() <= 4) {
            return "****";
        }

        return "****" + normalizedValue.substring(normalizedValue.length() - 4);
    }
}