package com.thofficialeng.preflightcards.domain.entity;

import jakarta.persistence.*;

import java.net.URL;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,length = 120)
    private String topic;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column
    private URL url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tone tone = Tone.CONCISE;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public Session() {}

    public Session(UUID id, String topic, String notes, URL url, Tone tone, Instant createdAt) {
        this.id = id;
        this.topic = topic;
        this.notes = notes;
        this.url = url;
        this.tone = tone;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Tone getTone() {
        return tone;
    }

    public void setTone(Tone tone) {
        this.tone = tone;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
