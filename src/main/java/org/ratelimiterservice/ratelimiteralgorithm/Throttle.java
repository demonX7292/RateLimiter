package org.ratelimiterservice.ratelimiteralgorithm;

public interface Throttle {
    boolean isRequestAllowed();
    long getTokensLeft();
    double getThrottleRate();
}
