package com.thofficialeng.preflightcards.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thofficialeng.preflightcards.domain.entity.Card;
import com.thofficialeng.preflightcards.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SessionService {

    private static final String bulletsJson =
            "[\"keep it short\",\"State goal\",\"Note constraints\"]";

    private final CardRepository cardRepository;
    private final ObjectMapper objectMapper;

    public SessionService(CardRepository cardRepository, ObjectMapper objectMapper) {
        this.cardRepository = cardRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public UUID create() {
        RateLimiter.assertWithinLimit();

        UUID sessionId = UUID.randomUUID();

        cardRepository.saveAll(List.of(
                new Card(sessionId, 0, "Know in 30s", bulletsJson),
                new Card(sessionId, 1, "Ask These", bulletsJson),
                new Card(sessionId, 2, "Say This", bulletsJson),
                new Card(sessionId, 3, "Quick Facts", bulletsJson),
                new Card(sessionId, 4, "Next Steps", bulletsJson)
        ));

        return sessionId;
    }

    public SessionView getView(UUID sessionId) {
        var cards = cardRepository.findBySessionIdOrderByIndexAsc(sessionId);
        var cardViews = cards.stream()
                .map(c -> new CardView(
                        c.getIndex(),
                        c.getTitle(),
                        parseBullets(c.getBulletsJson())
                ))
                .toList();

        return new SessionView(sessionId, cardViews);
    }

    private List<String> parseBullets(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            return List.of();
        }
    }

    public record SessionView(UUID sessionId, List<CardView> cards) {}
    public record CardView(int index, String title, List<String> bullets) {}
}
