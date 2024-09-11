package org.ratelimiterservice.models;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RateLimiterResponse {
    private final boolean isAllowed;
    private final String message;

    @Override
    public String toString() {
        String answer = isAllowed ? "Request Allowed" : "Request Denied";
        return answer + "\t" + message;
    }
}
