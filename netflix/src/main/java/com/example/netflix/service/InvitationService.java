package com.example.netflix.service;

import com.example.netflix.dto.GenreDTO;
import com.example.netflix.dto.GenreViewCount;
import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Invitation;
import com.example.netflix.repository.GenreDTORepository;
import com.example.netflix.repository.GenreRepository;
import com.example.netflix.repository.GenreViewCountRepository;
import com.example.netflix.repository.InvitationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class InvitationService
{
    private InvitationRepository invitationRepository;

    public InvitationService(InvitationRepository invitationRepository)
    {
        this.invitationRepository = invitationRepository;
    }

    public InvitationRepository getInvitationRepository()
    {
        return this.invitationRepository;
    }

    public void setInvitationRepository(InvitationRepository invitationRepository)
    {
        this.invitationRepository = invitationRepository;
    }
}
