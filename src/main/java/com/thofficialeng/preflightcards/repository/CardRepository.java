package com.thofficialeng.preflightcards.repository;

import com.thofficialeng.preflightcards.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    List<Card> findBySessionIdOrderByIndexAsc(UUID sessionId);
}
