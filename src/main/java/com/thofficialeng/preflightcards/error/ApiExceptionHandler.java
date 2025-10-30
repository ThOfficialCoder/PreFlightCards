package com.thofficialeng.preflightcards.error;

import com.thofficialeng.preflightcards.service.RateLimiter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RateLimiter.RateLimitExceededException.class)
    public ResponseEntity<String> handleRateLimit(RateLimiter.RateLimitExceededException ex) {
        long seconds = Math.max(1, ex.getRetryAfterMs() / 1000);
        return ResponseEntity
                .status(429)
                .header("Retry-After", String.valueOf(seconds))
                .body("Too many requests. Retry after " + seconds + "s.");
    }
}
