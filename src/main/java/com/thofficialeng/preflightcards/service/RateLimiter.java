package com.thofficialeng.preflightcards.service;

import java.time.Duration;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;

public class RateLimiter {

    private static final int MAX_ACTIONS = 20;
    private static final long WINDOW_MS = Duration.ofMinutes(1).toMillis();

    private static final ConcurrentMap<String, Deque<Long>> buckets = new ConcurrentHashMap<>();

    private RateLimiter() {}

    public static void assertWithinLimit() {
        assertWithinLimit("global");
    }

    public static void assertWithinLimit(String key) {
        final long now = System.currentTimeMillis();
        final Deque<Long> q = buckets.computeIfAbsent(key, k -> new ConcurrentLinkedDeque<>());

        while (true) {
            Long head = q.peekFirst();
            if (head == null || (now - head) < WINDOW_MS) break;
            q.pollFirst();
        }

        if (q.size() >= MAX_ACTIONS) {
            long retryAfterMs = WINDOW_MS - (now - q.peekFirst());
            throw new RateLimitExceededException(retryAfterMs);
        }

        q.addLast(now);
    }

    public static final class RateLimitExceededException extends RuntimeException {
        private final long retryAfterMs;

        public RateLimitExceededException(long retryAfterMs) {
            super("Rate limit exceeded. Try again later.");
            this.retryAfterMs = Math.max(retryAfterMs, 0);
        }
        public long getRetryAfterMs() { return retryAfterMs; }
    }
}
