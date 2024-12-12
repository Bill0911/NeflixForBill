package com.example.netflix.service;

import com.example.netflix.entity.GenreForSeries;
import com.example.netflix.id.GenreForSeriesId;
import com.example.netflix.repository.GenreForSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreForSeriesService
{

    @Autowired
    private GenreForSeriesRepository genreForSeriesRepository;

    public GenreForSeries save(GenreForSeries genreForSeries)
    {
        return genreForSeriesRepository.save(genreForSeries);
    }

    public GenreForSeries findById(GenreForSeriesId id)
    {
        return genreForSeriesRepository.findById(id).orElse(null);
    }

    public List<GenreForSeries> findAll()
    {
        return genreForSeriesRepository.findAll();
    }

    public GenreForSeries update(GenreForSeriesId id, GenreForSeries genreForSeries)
    {
        if (genreForSeriesRepository.existsById(id))
        {
            genreForSeries.setGenreId(id.getGenreId());
            genreForSeries.setSeriesId(id.getSeriesId());
            return genreForSeriesRepository.save(genreForSeries);
        }
        return null;
    }

    public void delete(GenreForSeriesId id)
    {
        genreForSeriesRepository.deleteById(id);
    }
}