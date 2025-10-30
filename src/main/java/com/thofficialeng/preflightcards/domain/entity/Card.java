package com.thofficialeng.preflightcards.domain.entity;

import jakarta.persistence.*;

import java.net.URL;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cards", indexes = {
        @Index(name = "idx_cards_session", columnList = "sessionId, idx")
})
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Store just the foreign key for simplicity (no relation object)
    @Column(nullable = false, columnDefinition = "UUID")
    private UUID sessionId;

    // 'index' is a reserved word in some DBs; use 'idx' as column name
    @Column(name = "idx", nullable = false)
    private int index;

    @Column(nullable = false, length = 50)
    private String title;

    // JSON array of strings like ["bullet 1","bullet 2",...]
    @Column(nullable = false, columnDefinition = "TEXT")
    private String bulletsJson;

    public Card() {}

    public Card(UUID sessionId, int index, String title, String bulletsJson) {
        this.sessionId = sessionId;
        this.index = index;
        this.title = title;
        this.bulletsJson = bulletsJson;
    }

    public UUID getId() { return id; }
    public UUID getSessionId() { return sessionId; }
    public int getIndex() { return index; }
    public String getTitle() { return title; }
    public String getBulletsJson() { return bulletsJson; }

    public void setSessionId(UUID sessionId) { this.sessionId = sessionId; }
    public void setIndex(int index) { this.index = index; }
    public void setTitle(String title) { this.title = title; }
    public void setBulletsJson(String bulletsJson) { this.bulletsJson = bulletsJson; }
}
