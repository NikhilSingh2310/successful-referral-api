package com.simplifymoney.successfulreferral.dto;

/**
 * Represents a user signup request, including optional referral code.
 */
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String referralCode;

    // Default constructor for serialization
    public SignupRequest() {}

    // Constructor to initialize all fields
    public SignupRequest(String name, String email, String password, String referralCode) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.referralCode = referralCode;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getReferralCode() { return referralCode; }
    public void setReferralCode(String referralCode) { this.referralCode = referralCode; }

    // Debugging utility
    @Override
    public String toString() {
        return "SignupRequest{name='" + name + "', email='" + email + "', password='" + password + "', referralCode='" + referralCode + "'}";
    }
}
