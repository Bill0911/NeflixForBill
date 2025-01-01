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
    private SubscriptionType subscription = SubscriptionType.SD; // Default subscription

    @Column(name = "trial_start_date", nullable = false)
    private LocalDateTime trialStartDate = LocalDateTime.now(); // Default to current time

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.JUNIOR; // Default role

    @Column(name = "active", columnDefinition = "bit(1) DEFAULT 0")
    private boolean isActive = false;

    @Column(name = "blocked", columnDefinition = "bit(1) DEFAULT 0")
    private boolean isBlocked = false;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Profile> profiles;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)  // Foreign key to Language table
    private Language language;

    @Column(name = "failed_attempts", nullable = false)
    private int failedLoginAttempts = 0;

    @Column(name = "lock_time")
    private LocalDateTime lockTime = null;

    @Column(name = "discount", nullable = false)
    private boolean discount = false;

    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public User()
    {}

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

    public SubscriptionType getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionType subscription) {
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

    public boolean isActive ()
    {
        return isActive;
    }

    public void setActive (boolean active)
    {
        isActive = active;
    }

    public boolean isBlocked ()
    {
        return isBlocked;
    }

    public void setBlocked (boolean blocked)
    {
        isBlocked = blocked;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isIsBlocked () {
        return isBlocked;
    }

    public void setIsBlocked (boolean blocked) {
        this.isBlocked = blocked;
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

    public int getFailedLoginAttempts () {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts (int failedAttempts) {
        this.failedLoginAttempts = failedAttempts;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    public void incrementFailedAttempts () {
        this.failedLoginAttempts++;
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

    public boolean isDiscount ()
    {
        return discount;
    }

    public void setDiscount (boolean discount)
    {
        this.discount = discount;
    }
}