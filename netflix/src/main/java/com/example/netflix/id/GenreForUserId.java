package com.example.netflix.id;

import com.example.netflix.entity.Genre;
import com.example.netflix.entity.Profile;
import com.example.netflix.entity.User;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

public class GenreForUserId implements Serializable {

    private User user;
    private Genre genre;

    public GenreForUserId() {}

    public GenreForUserId(User user, Genre genre) {
        this.user = user;
        this.genre = genre;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Genre getGenre()
    {
        return genre;
    }

    public void setGenre(Genre genre)
    {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreForUserId that = (GenreForUserId) o;
        return Objects.equals(user.getAccountId(), that.user.getAccountId()) &&
                Objects.equals(genre.getGenreId(), that.genre.getGenreId());
    }

    // hashCode() method: generate hash code based on accountId and movieId
    @Override
    public int hashCode() {
        return Objects.hash(user.getAccountId(), genre.getGenreId());
    }
}
