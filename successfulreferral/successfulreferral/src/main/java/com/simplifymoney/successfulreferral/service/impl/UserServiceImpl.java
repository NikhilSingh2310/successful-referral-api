package com.simplifymoney.successfulreferral.service.impl;

import com.simplifymoney.successfulreferral.dto.UserDTO;
import com.simplifymoney.successfulreferral.entity.User;
import com.simplifymoney.successfulreferral.mapper.UserMapper;
import com.simplifymoney.successfulreferral.repository.UserRepository;
import com.simplifymoney.successfulreferral.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves all users referred by a specific referrer.
     * Marks referrals as successful only if both referrer and referred user have completed their profiles.
     */
    @Override
    public List<UserDTO> getReferrals(Long userId) {
        User referrer = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> referredUsers = userRepository.findByReferrer(referrer);

        return referredUsers.stream()
                .map(user -> {
                    UserDTO dto = UserMapper.toDTO(user);
                    dto.setProfileComplete(user.isProfileComplete());
                    dto.setReferralComplete(user.isReferralComplete());
                    return dto;
                })
                .toList();
    }

    /**
     * Handles user signup, generates a unique referral code, and links the referrer if the referral code is valid.
     * Prevents using a referral code if the referrer has not completed their profile.
     */
    @Override
    public UserDTO signup(String name, String email, String password, String referralCode) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setReferralCode(generateReferralCode());

        if (referralCode != null && !referralCode.isEmpty()) {
            Optional<User> referrerOpt = userRepository.findByReferralCode(referralCode);
            if (referrerOpt.isPresent()) {
                User referrer = referrerOpt.get();

                if (!referrer.isProfileComplete()) {
                    throw new RuntimeException("Referral code cannot be used as the referrer has not completed their profile.");
                }
                user.setReferrer(referrer);
            } else {
                throw new RuntimeException("Invalid referral code");
            }
        }

        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    /**
     * Marks a user's profile as complete and updates referral status accordingly.
     * If both the referred user and referrer have completed profiles, marks the referral as successful.
     */
    @Override
    public void completeProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setProfileComplete(true);
        userRepository.save(user);

        // Check if this user was referred by someone
        if (user.getReferrer() != null) {
            User referrer = user.getReferrer();
            if (referrer.isProfileComplete() && user.isProfileComplete()) {
                user.setReferralComplete(true);
                userRepository.save(user);
                System.out.println("Referral marked as successful for: " + user.getEmail());
            }
        }

        // Check if this user has referred others
        List<User> referredUsers = userRepository.findByReferrer(user);
        for (User referredUser : referredUsers) {
            if (referredUser.isProfileComplete() && user.isProfileComplete()) {
                referredUser.setReferralComplete(true);
                userRepository.save(referredUser);
                System.out.println("Referral marked as successful for: " + referredUser.getEmail());
            }
        }
    }

    /**
     * Generates a CSV report of all user referrals, including referrer details, referred users, and referral status.
     */
    @Override
    public void generateReferralReport(PrintWriter writer) {
        List<User> users = userRepository.findAll();
        writer.println("Referrer Name,Referred Name,Referred Email,Referral Status");

        for (User referredUser : users) {
            if (referredUser.getReferrer() != null) {
                String referrerName = referredUser.getReferrer().getName();
                String referredName = referredUser.getName();
                String referredEmail = referredUser.getEmail();
                String status = referredUser.isReferralComplete() ? "Successful" : "Pending";

                writer.println(referrerName + "," + referredName + "," + referredEmail + "," + status);
            }
        }
    }

    /**
     * Finds a user by email and returns a DTO.
     */
    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserMapper::toDTO);
    }

    /**
     * Finds a user by referral code and returns a DTO.
     */
    @Override
    public Optional<UserDTO> findByReferralCode(String referralCode) {
        return userRepository.findByReferralCode(referralCode).map(UserMapper::toDTO);
    }

    /**
     * Generates a unique referral code for each user.
     */
    private String generateReferralCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
