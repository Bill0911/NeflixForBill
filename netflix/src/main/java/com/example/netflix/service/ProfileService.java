package com.example.netflix.service;

import com.example.netflix.dto.MethodResponse;
import com.example.netflix.entity.*;
import com.example.netflix.repository.MovieRepository;
import com.example.netflix.repository.ProfileRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final MovieRepository movieRepository;

    public ProfileService(UserRepository userRepository, ProfileRepository profileRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.movieRepository = movieRepository;
    }

    public boolean fitsForMovieWatching(Integer profileId, Integer movieId, Integer accountId)
    {

        Optional<Profile> profileOptional = profileRepository.findByProfileId(profileId);
        Optional<Movie> movieOptional = movieRepository.findByMovieId(movieId);
        Optional<User> userOptional = userRepository.findByAccountId(accountId);


        if(movieOptional.isEmpty() || profileOptional.isEmpty() || userOptional.isEmpty() || !profileBelongsToUser(profileId, accountId)) {
            return false;
        }

        if (profileOptional.get().getAge() < movieOptional.get().getMinimumAge())
        {
            return false;
        }

        return true;
    }

    public boolean profileBelongsToUser(Integer profileId, Integer accountId)
    {
        for (Profile profile : this.profileRepository.findProfilesByAccountId(accountId)) {
            if (Objects.equals(profile.getProfileId(), profileId)) {
                return true;
            }
        }

        return false;
    }

    public void addProfile(Profile profile) {
        if (profileRepository.findProfilesByAccountId(profile.getUser()).size() >= 4) {
            throw new RuntimeException("4 profiles max for 1 user");
        }
        profileRepository.addProfile(profile.getUser(), profile.getProfileImage(), profile.getAge(), profile.getName());
    }

    public Profile getProfileById(Integer id) {
        Optional<Profile> profile = profileRepository.findByProfileId(id);
        return profile.orElse(null);
    }

    public List<Profile> getManyProfiles() {
        return profileRepository.findMany();
    }

    public void deleteProfileById(Integer id) {
        profileRepository.deleteByProfileId(id);
    }

    public void patchProfileById(Integer id, Profile user) {
        profileRepository.patchByProfileId(id, user.getUser(), user.getProfileImage(), user.getAge(), user.getName());
    }

    public void updateProfileById(Integer id, Profile user) {
        profileRepository.updateByProfileId(id, user.getUser(), user.getProfileImage(), user.getAge(), user.getName());
    }
}
