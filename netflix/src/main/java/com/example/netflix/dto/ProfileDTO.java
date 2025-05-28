package com.example.netflix.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "profile")
public class ProfileDTO {
    private Integer profileId;
    private String name;
    private Integer user;  // match with entity field name
    private String profileImage;
    private Integer age;

    public ProfileDTO() {}

    public ProfileDTO(Integer profileId, String name, Integer user, String profileImage, Integer age) {
        this.profileId = profileId;
        this.name = name;
        this.user = user;
        this.profileImage = profileImage;
        this.age = age;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
