package com.city.profile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile_media")
public class ProfileMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Profile profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MediaType type; // PHOTO, VIDEO, REEL

    @Column(nullable = false)
    private String url;

    private String thumbnail; // para videos/reels

    private String title;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum MediaType {
        PHOTO, VIDEO, REEL
    }

    public ProfileMedia() {}

    // Getters y Setters
    public Long getId() { return id; }
    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }
    public MediaType getType() { return type; }
    public void setType(MediaType type) { this.type = type; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}