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

    public MethodResponse fitsMovieAgeRestrictions(Integer profileId, Integer movieId)
    {
        MethodResponse methodResponse = new MethodResponse();

        Optional<Profile> profileOptional = profileRepository.findByProfileId(profileId);
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        if(movieOptional.isEmpty() || profileOptional.isEmpty()) {
            methodResponse.setMessage("No such profile or movie");
            return methodResponse;
        }

        if (profileOptional.get().getAge() >= movieOptional.get().getMinimumAge())
        {
            methodResponse.setMessage("You fits movie's age restrictions!");
            methodResponse.setSuccess(true);
            return methodResponse;
        }

        methodResponse.setMessage("You cannot watch movie due to age restrictions");

        return methodResponse;
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
