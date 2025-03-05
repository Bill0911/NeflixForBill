package com.example.netflix.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "invitation")
public class Invitation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "inviter_id", nullable = false)
    private Integer inviter;

    @Column(name = "invitee_id", nullable = false)
    private Integer invitee;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getInviter()
    {
        return inviter;
    }

    public void setInviter(Integer inviter)
    {
        this.inviter = inviter;
    }

    public Integer getInvitee()
    {
        return invitee;
    }

    public void setInvitee(Integer invitee)
    {
        this.invitee = invitee;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }
}
