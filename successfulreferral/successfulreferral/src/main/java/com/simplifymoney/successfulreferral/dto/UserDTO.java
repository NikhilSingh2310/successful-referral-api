package com.simplifymoney.successfulreferral.dto;

/**
 * Data Transfer Object (DTO) representing user details, including referral status.
 */
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String referralCode;
    private boolean profileComplete;
    private boolean referralComplete;

    // Default constructor for serialization
    public UserDTO() {}

    // Constructor to initialize all fields
    public UserDTO(Long id, String name, String email, String referralCode, boolean profileComplete, boolean referralComplete) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.referralCode = referralCode;
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

    public String getReferralCode() { return referralCode; }
    public void setReferralCode(String referralCode) { this.referralCode = referralCode; }

    public boolean isProfileComplete() { return profileComplete; }
    public void setProfileComplete(boolean profileComplete) { this.profileComplete = profileComplete; }

    public boolean isReferralComplete() { return referralComplete; }
    public void setReferralComplete(boolean referralComplete) { this.referralComplete = referralComplete; }
}
