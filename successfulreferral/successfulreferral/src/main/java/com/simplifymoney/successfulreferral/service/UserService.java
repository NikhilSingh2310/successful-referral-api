package com.simplifymoney.successfulreferral.service;

import com.simplifymoney.successfulreferral.dto.UserDTO;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing user operations and referral tracking.
 */
public interface UserService {

    /**
     * Registers a new user and associates them with a referrer if a referral code is provided.
     * @param name User's name.
     * @param email User's email.
     * @param password User's password.
     * @param referralCode Optional referral code for tracking referrals.
     * @return The created user as a DTO.
     */
    UserDTO signup(String name, String email, String password, String referralCode);

    /**
     * Marks a user's profile as complete and updates referral status accordingly.
     * @param userId The ID of the user completing their profile.
     */
    void completeProfile(Long userId);

    /**
     * Retrieves a user by email.
     * @param email The email to search for.
     * @return An Optional containing the user DTO if found.
     */
    Optional<UserDTO> findByEmail(String email);

    /**
     * Retrieves a user by their referral code.
     * @param referralCode The referral code to search for.
     * @return An Optional containing the user DTO if found.
     */
    Optional<UserDTO> findByReferralCode(String referralCode);

    /**
     * Fetches a list of users referred by a specific user.
     * @param userId The ID of the referrer.
     * @return A list of referred users as DTOs.
     */
    List<UserDTO> getReferrals(Long userId);

    /**
     * Generates a CSV report of all user referrals and writes it to the provided writer.
     * @param writer The PrintWriter to write the CSV report.
     */
    void generateReferralReport(PrintWriter writer);
}
