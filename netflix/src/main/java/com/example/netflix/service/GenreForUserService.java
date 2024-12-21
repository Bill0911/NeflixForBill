package com.example.netflix.service;

import com.example.netflix.entity.GenreForUser;
import com.example.netflix.id.GenreForUserId;
import com.example.netflix.repository.GenreForUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreForUserService {

    private final GenreForUserRepository genreForUserRepository;

    public GenreForUserService(GenreForUserRepository genreForUserRepository) {
        this.genreForUserRepository = genreForUserRepository;
    }

    public List<GenreForUser> getAllGenreForUsers() {
        return genreForUserRepository.findAll();
    }

    public GenreForUser getGenreForUserById(Integer accountId, Integer genreId) {
        return genreForUserRepository.findById(new GenreForUserId(accountId, genreId))
                .orElseThrow(() -> new RuntimeException("GenreForUser not found"));
    }

    public GenreForUser addGenreForUser(GenreForUser genreForUser) {
        return genreForUserRepository.save(genreForUser);
    }

    public GenreForUser updateGenreForUser(GenreForUser genreForUser) {
        return genreForUserRepository.save(genreForUser);
    }

    public void deleteGenreForUser(Integer accountId, Integer genreId) {
        genreForUserRepository.deleteById(new GenreForUserId(accountId, genreId));
    }

    public GenreForUser patchGenreForUser(Integer accountId, Integer genreId, GenreForUser patchData) {
        GenreForUser genreForUser = genreForUserRepository.findById(new GenreForUserId(accountId, genreId))
                .orElseThrow(() -> new RuntimeException("GenreForUser not found"));

        if (patchData.getGenre() != null) {
            genreForUser.setGenre(patchData.getGenre());
        }

        return genreForUserRepository.save(genreForUser);
    }
}
