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

    public

    public List<Invitation> getAllGenres() {
        return invitationRepository.findMany();
    }

    public Optional<Genre> getGenreById(Integer id) {
        return genreRepository.findByGenreId(id);
    }

    public void addGenre(String genreName) {
        System.out.println("Genre has not been added yet");
        genreRepository.addGenre(genreName);
        System.out.println("Genre has been added ");
    }

    public void updateGenre(Integer id, String genreName) {
        genreRepository.updateById(id, genreName);;
    }

    public Genre patchGenre(Integer id, Genre patchData) {
        return genreRepository.findById(id)
                .map(existingGenre -> {
                    if (patchData.getGenreName() != null) {
                        existingGenre.setGenreName(patchData.getGenreName());
                    }
                    return genreRepository.save(existingGenre);
                })
                .orElseThrow(() -> new RuntimeException("Genre not found with ID: " + id));
    }

    public void deleteGenre(Integer id) {
        genreRepository.deleteById(id);
    }

    public List<GenreDTO> getGenresWithoutMovie() {
        return  genreDTORepository.findGenresWithoutMovie();
    }

    public List<GenreViewCount> getViewCounts() {
        return  genreViewCountRepository.findGenreViewCounts();
    }
}
