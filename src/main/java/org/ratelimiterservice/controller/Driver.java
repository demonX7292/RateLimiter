package org.ratelimiterservice.controller;

import org.ratelimiterservice.config.RateLimitingConfiguration;
import org.ratelimiterservice.models.RateLimiterRequest;
import org.ratelimiterservice.models.RateLimiterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.ratelimiterservice.config.Constants.THROTTLE_RATE;
import static org.ratelimiterservice.config.Constants.TOKEN_BUCKET_MAX_TOKENS;

@RestController
public class Driver {

    private final RateLimiterHandler rateLimiterHandler;

    @Autowired
    public Driver() {
        RateLimitingConfiguration configuration = new RateLimitingConfiguration();
        configuration.getConfigMap().put(TOKEN_BUCKET_MAX_TOKENS, 5L);
        configuration.getConfigMap().put(THROTTLE_RATE, 0.2);
        rateLimiterHandler = new RateLimiterHandler(RateLimiterType.TOKEN_BUCKET, configuration);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/rate-limiter")
    public String pingServer(@RequestBody RateLimiterRequest requestId) {
        return rateLimiterHandler.isAllowed(requestId.getRequestId()).toString();
    }
}
