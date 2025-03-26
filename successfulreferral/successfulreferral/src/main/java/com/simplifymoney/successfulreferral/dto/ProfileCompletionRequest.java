package com.simplifymoney.successfulreferral.dto;

public class ProfileCompletionRequest {
    private Long userId;

    // Default Constructor
    public ProfileCompletionRequest() {}

    // Parameterized Constructor (Optional)
    public ProfileCompletionRequest(Long userId) {
        this.userId = userId;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    // toString() for debugging (Optional)
    @Override
    public String toString() {
        return "ProfileCompletionRequest{userId=" + userId + "}";
    }
}
