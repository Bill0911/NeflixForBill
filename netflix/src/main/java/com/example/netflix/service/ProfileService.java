package com.example.netflix.service;

import com.example.netflix.entity.*;
import com.example.netflix.repository.LanguageRepository;
import com.example.netflix.repository.MovieRepository;
import com.example.netflix.repository.ProfileRepository;
import com.example.netflix.repository.UserRepository;
import com.example.netflix.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public MethodResponse belongsToUser(Integer profileId, Integer accountId)
    {
        MethodResponse methodResponse = new MethodResponse();

        Optional<User> userOptional = userRepository.findByAccountId(accountId);
        Optional<Profile> profileOptional = profileRepository.findByProfileId(profileId);

        if(userOptional.isEmpty() || profileOptional.isEmpty()) {
            methodResponse.setMessage("No such profile");
            return methodResponse;
        }

        for (Profile profile : userOptional.get().getProfiles()) {
            if (Objects.equals(profile.getProfileId(), profileId)) {
                methodResponse.setMessage("Profile belongs to user!");
                methodResponse.setSuccess(true);
                return methodResponse;
            }
        }

        methodResponse.setMessage("Profile does not belong to user");

        return methodResponse;
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
}
