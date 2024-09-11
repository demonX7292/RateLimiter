package org.ratelimiterservice.controller;

import org.ratelimiterservice.config.RateLimitingConfiguration;
import org.ratelimiterservice.models.RateLimiterResponse;
import org.ratelimiterservice.models.RateLimiterType;
import org.ratelimiterservice.ratelimiteralgorithm.Throttle;

import java.util.HashMap;
import java.util.Map;

public class RateLimiterHandler {

    private final RateLimiterFactory rateLimiterFactory;
    private final RateLimiterType rateLimiterType;
    private final Map<String, Throttle> throttleMap;
    private final RateLimitingConfiguration configuration;

    public RateLimiterHandler(RateLimiterType rateLimiterType,
                              RateLimitingConfiguration configuration) {
        rateLimiterFactory = RateLimiterFactory.getInstance();
        throttleMap = new HashMap<>();
        this.rateLimiterType = rateLimiterType;
        this.configuration = configuration;
    }

    public RateLimiterResponse isAllowed(String id) {
        if (throttleMap.get(id) == null) {
            throttleMap.put(id, rateLimiterFactory.getRateLimiter(rateLimiterType.toString(), configuration));
        }
        return buildResponse(id);
    }

    private RateLimiterResponse buildResponse(String id) {
        if (throttleMap.get(id).isRequestAllowed()) {
            return new RateLimiterResponse(true, "Tokens Left : " + throttleMap.get(id).getTokensLeft());
        }
        return new RateLimiterResponse(false, "No Tokens Available");
    }

}
