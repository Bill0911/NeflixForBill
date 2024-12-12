package com.example.netflix.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "episode")
public class Episode
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episode_id")
    private Integer episodeId;

    @Column(nullable = false)
    private String title;

    @Column(name = "duration", columnDefinition = "TIME DEFAULT '00:00:00'")
    private LocalTime duration;

    @ManyToOne
    @JoinColumn(name = "series_id", insertable = false, updatable = false)
    private Series series;

    public Episode ()
    {
        this.duration = LocalTime.of(0,0,0);
    }

    public Episode (String title, LocalTime duration)
    {
        this.title = title;
        this.duration = duration != null ? duration : LocalTime.of(0, 0, 0);
    }
}
