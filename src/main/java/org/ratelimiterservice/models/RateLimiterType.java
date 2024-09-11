package org.ratelimiterservice.models;

public enum RateLimiterType {
    TOKEN_BUCKET("TOKEN_BUCKET"),
    SLIDING_WINDOW("SLIDING_WINDOW");

    private final String rateLimiterType;

    RateLimiterType(String rateLimiterType) {
        this.rateLimiterType = rateLimiterType;
    }

    @Override
    public String toString() {
        return rateLimiterType;
    }
}
