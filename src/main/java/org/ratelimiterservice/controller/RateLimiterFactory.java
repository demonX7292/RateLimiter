package org.ratelimiterservice.controller;

import org.ratelimiterservice.config.RateLimitingConfiguration;
import org.ratelimiterservice.models.RateLimiterType;
import org.ratelimiterservice.ratelimiteralgorithm.Throttle;
import org.ratelimiterservice.ratelimiteralgorithm.TokenBucketRateLimiter;

public class RateLimiterFactory {

    private static volatile RateLimiterFactory rateLimiterFactory;

    public static RateLimiterFactory getInstance() {
        if (rateLimiterFactory != null) {
            return rateLimiterFactory;
        }
        synchronized (RateLimiterFactory.class) {
            if (rateLimiterFactory == null) {
                rateLimiterFactory = new RateLimiterFactory();
            }
            return rateLimiterFactory;
        }
    }

    public Throttle getRateLimiter(String rateLimiterType, RateLimitingConfiguration configuration) {
       switch (RateLimiterType.valueOf(rateLimiterType)) {
           case TOKEN_BUCKET -> {
               return new TokenBucketRateLimiter(configuration);
           }
       }
       return new TokenBucketRateLimiter(configuration);
    }
}
