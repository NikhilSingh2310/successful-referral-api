package com.simplifymoney.successfulreferral.repository;

import com.simplifymoney.successfulreferral.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity, providing database operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by email.
     * @param email The email to search for.
     * @return An Optional containing the user if found.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their unique referral code.
     * @param referralCode The referral code to search for.
     * @return An Optional containing the user if found.
     */
    Optional<User> findByReferralCode(String referralCode);

    /**
     * Retrieves all users referred by a specific user.
     * @param referrer The referring user.
     * @return A list of referred users.
     */
    List<User> findByReferrer(User referrer);
}
