package com.example.netflix.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Integer languageId;

    @Column(nullable = false)
    private String name;

    public Language(Integer languageId, String name)
    {
        this.languageId = languageId;
        this.name = name;
    }

    public Integer getLanguageId()
    {
        return this.languageId;
    }

    public void setLanguageId(Integer languageId)
    {
        this.languageId = languageId;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
