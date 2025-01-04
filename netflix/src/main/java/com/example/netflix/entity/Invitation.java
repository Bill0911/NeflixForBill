package com.example.netflix.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Invitation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "inviter_id", nullable = false)
    private User inviter;

    @ManyToOne
    @JoinColumn(name = "invitee_id", nullable = false)
    private User invitee;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public User getInviter()
    {
        return inviter;
    }

    public void setInviter(User inviter)
    {
        this.inviter = inviter;
    }

    public User getInvitee()
    {
        return invitee;
    }

    public void setInvitee(User invitee)
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
