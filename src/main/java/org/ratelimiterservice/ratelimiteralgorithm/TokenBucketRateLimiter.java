package org.ratelimiterservice.ratelimiteralgorithm;


import lombok.Getter;
import org.ratelimiterservice.config.RateLimitingConfiguration;

import static org.ratelimiterservice.config.Constants.THROTTLE_RATE;
import static org.ratelimiterservice.config.Constants.TOKEN_BUCKET_MAX_TOKENS;

public class TokenBucketRateLimiter implements Throttle {

    @Getter
    private final long maxNumberOfTokens;
    @Getter
    private final double newTokensPerSecond;
    private long timeOfLastRefill;
    private volatile long numberOfTokens;


    public TokenBucketRateLimiter(long maxTokens, double throttleRate) {
        if (maxTokens <= 0 || throttleRate <= 0.0) {
            throw new IllegalArgumentException
                    ("maxTokens and throttleRate must be > 0");
        }
        maxNumberOfTokens = maxTokens;
        newTokensPerSecond = throttleRate;
        // Initially the bucket is full of tokens
        numberOfTokens = maxNumberOfTokens;
        timeOfLastRefill = System.currentTimeMillis();
    }

    public TokenBucketRateLimiter(RateLimitingConfiguration configuration) {
        this((long)configuration.getConfigMap().get(TOKEN_BUCKET_MAX_TOKENS),
                (double)configuration.getConfigMap().get(THROTTLE_RATE));
    }


    /**
     * Request a token from the bucket. If there is a token in the bucket,
     * decrement the token count and return true. If there are no tokens in the
     * bucket, we do NOT decrement the token count and return false.
     */
    @Override
    public synchronized boolean isRequestAllowed() {
        refillTokens();
        if (numberOfTokens < 1)
            return false;
        --numberOfTokens;
        return true;
    }


    // Only one thread should be refilling at a time...
    private synchronized void refillTokens() {
        long now = System.currentTimeMillis();
        long tokensToAdd = (long)((now - timeOfLastRefill) * 0.001 * newTokensPerSecond);
        if (tokensToAdd <= 0) {
            return;
        }
        numberOfTokens += tokensToAdd;
        numberOfTokens = Math.min(numberOfTokens, maxNumberOfTokens);
        timeOfLastRefill = now;
    }

    /**
     * @return the throttle rate (which is also the number of tokens we put back
     *         into the bucket every second)
     */
    public double getThrottleRate() {
        return newTokensPerSecond;
    }

    @Override
    public long getTokensLeft() {
        return numberOfTokens;
    }

    public synchronized String toString() {
        return "TokenBucket: currentTokenCount=" + (long) numberOfTokens
                + "; maxTokens=" + maxNumberOfTokens + "; tokensPerSecond="
                + newTokensPerSecond;
    }
}
