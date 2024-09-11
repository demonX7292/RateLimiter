package org.ratelimiterservice.config;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RateLimitingConfiguration {
    private final Map<String, Object> configMap = new HashMap<>();
}
