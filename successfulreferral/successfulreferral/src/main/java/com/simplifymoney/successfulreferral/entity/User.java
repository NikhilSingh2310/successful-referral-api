package com.simplifymoney.successfulreferral.entity;

import jakarta.persistence.*;

/**
 * Represents a user in the referral system, including referral tracking.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // Stored as plain text

    @Column(nullable = false, unique = true, length = 10)
    private String referralCode; // Unique referral code assigned to each user

    @ManyToOne
    @JoinColumn(name = "referrer_id")
    private User referrer; // Self-referencing field to track referrals

    @Column(nullable = false)
    private boolean profileComplete = false; // Indicates if the user has completed their profile

    @Column(nullable = false)
    private boolean referralComplete = false; // Indicates if the referral is considered successful

    // Default Constructor
    public User() {}

    // Parameterized Constructor
    public User(Long id, String name, String email, String password, String referralCode, User referrer, boolean profileComplete, boolean referralComplete) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.referralCode = referralCode;
        this.referrer = referrer;
        this.profileComplete = profileComplete;
        this.referralComplete = referralComplete;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getReferralCode() { return referralCode; }
    public void setReferralCode(String referralCode) { this.referralCode = referralCode; }

    public User getReferrer() { return referrer; }
    public void setReferrer(User referrer) { this.referrer = referrer; }

    public boolean isProfileComplete() { return profileComplete; }
    public void setProfileComplete(boolean profileComplete) { this.profileComplete = profileComplete; }

    public boolean isReferralComplete() { return referralComplete; }
    public void setReferralComplete(boolean referralComplete) { this.referralComplete = referralComplete; }
}
