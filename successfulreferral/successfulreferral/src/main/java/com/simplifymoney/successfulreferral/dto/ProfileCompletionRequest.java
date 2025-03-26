package com.simplifymoney.successfulreferral.dto;

/**
 * Represents a request to mark a user's profile as complete.
 */
public class ProfileCompletionRequest {
    private Long userId;

    // Default constructor for serialization
    public ProfileCompletionRequest() {}

    // Constructor to initialize userId
    public ProfileCompletionRequest(Long userId) {
        this.userId = userId;
    }

    // Getter and Setter
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    // Debugging utility
    @Override
    public String toString() {
        return "ProfileCompletionRequest{userId=" + userId + "}";
    }
}
