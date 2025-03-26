package com.simplifymoney.successfulreferral.dto;

public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String referralCode;

    // Default Constructor
    public SignupRequest() {}

    // Parameterized Constructor (Optional)
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

    // toString() for debugging (Optional)
    @Override
    public String toString() {
        return "SignupRequest{name='" + name + "', email='" + email + "', password='" + password + "', referralCode='" + referralCode + "'}";
    }
}
