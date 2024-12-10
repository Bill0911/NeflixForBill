package com.example.netflix.service;

import com.example.netflix.entity.Series;
import com.example.netflix.entity.SeriesViewCount;
import com.example.netflix.entity.User;
import com.example.netflix.repository.SeriesViewCountRepository;
import com.example.netflix.repository.SeriesRepository;
import com.example.netflix.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeriesViewCountService
{
    private final SeriesViewCountRepository seriesViewCountRepository;
    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;

    public SeriesViewCountService(SeriesViewCountRepository seriesViewCountRepository, SeriesRepository seriesRepository, UserRepository userRepository)
    {
        this.seriesViewCountRepository = seriesViewCountRepository;
        this.seriesRepository = seriesRepository;
        this.userRepository = userRepository;
    }

    public void incrementViewCount(Integer account_id, Integer seriesId)
    {
        Optional<User> userOpt = userRepository.findById(account_id);
        Optional<Series> seriesOpt = seriesRepository.findById(seriesId);

        if (userOpt.isPresent() && seriesOpt.isPresent())
        {
            User user = userOpt.get();
            Series series = seriesOpt.get();
            SeriesViewCount seriesViewCount = seriesViewCountRepository.findByUser_AccountIdAndSeries_SeriesId(account_id, seriesId)
                    .orElse(new SeriesViewCount());
            System.out.println("The series / account exist");
            seriesViewCount.setUser(user);
            seriesViewCount.setSeries(series);
            seriesViewCount.incrementViewCount();
            System.out.println("The series id = " + seriesViewCount.getSeries().getSeriesId() + " and the account id = " + seriesViewCount.getUser().getAccountId() + ", views: " + seriesViewCount.getNumber());
            seriesViewCountRepository.save(seriesViewCount);
        }
        else
        {
            if (userOpt.isEmpty())
            {
                System.out.println("User with ID " + account_id + " does not exist");
            }
            if (seriesOpt.isEmpty())
            {
                System.out.println("Series with ID " + seriesId + " does not exist");
            }
        }
    }

    public void addSeriesToViewCount(Integer id, Integer seriesId)
    {
        Optional<User> userOpt = userRepository.findById(id);
        if(userOpt.isEmpty())
        {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        Optional<Series> seriesOpt = seriesRepository.findById(seriesId);

        if(seriesOpt.isEmpty())
        {
            throw new RuntimeException("Series not found");
        }

        Series series = seriesOpt.get();

        SeriesViewCount seriesViewCount = seriesViewCountRepository.findByUser_AccountIdAndSeries_SeriesId(user.getAccountId(), series.getSeriesId())
                .orElse(new SeriesViewCount());

        seriesViewCount.setUser(user);
        seriesViewCount.setSeries(series);
        seriesViewCount.incrementViewCount();
        seriesViewCountRepository.save(seriesViewCount);
    }
}
