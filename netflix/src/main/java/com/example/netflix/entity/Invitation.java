package com.example.netflix.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invitation")
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inviter_id")
    private Long inviterId;

    @Column(name = "invitee_id")
    private Long inviteeId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getInviterId() { return inviterId; }
    public void setInviterId(Long inviterId) { this.inviterId = inviterId; }

    public Long getInviteeId() { return inviteeId; }
    public void setInviteeId(Long inviteeId) { this.inviteeId = inviteeId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
