package com.example.netflix.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    public Integer accountId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subscription subscription = Subscription.SD; // Default subscription

    @Column(name = "trial_start_date", nullable = false)
    private LocalDateTime trialStartDate = LocalDateTime.now(); // Default to current time

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.VIEWER; // Default role

    @Column(nullable = false)
    private boolean blocked = false;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Profile> profiles;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)  // Foreign key to Language table
    private Language language;

    @Column(name = "failed_attempts", nullable = false)
    private int failedAttempts = 0;

    @Column(name = "lock_time")
    private LocalDateTime lockTime = null;

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public User()
    {

    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public LocalDateTime getTrialStartDate() {
        return trialStartDate;
    }

    public void setTrialStartDate(LocalDateTime trialStartDate) {
        this.trialStartDate = trialStartDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    public void incrementFailedAttempts () {
        this.failedAttempts++;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public void addProfile(Profile profile) {
        this.profiles.add(profile);
    }
}