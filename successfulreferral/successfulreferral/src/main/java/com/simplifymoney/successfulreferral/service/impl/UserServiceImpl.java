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

    @Override
    public List<UserDTO> getReferrals(Long userId) {
        User referrer = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<User> referredUsers = userRepository.findByReferrer(referrer);

        return referredUsers.stream()
                .map(user -> {
                    UserDTO dto = UserMapper.toDTO(user);
                    dto.setProfileComplete(user.isProfileComplete());

                    // ✅ Referral is successful only if BOTH profiles are complete
                    dto.setReferralComplete(user.isReferralComplete());

                    return dto;
                })
                .toList();
    }



    @Override
    public UserDTO signup(String name, String email, String password, String referralCode) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setReferralCode(generateReferralCode());

        // ✅ Prevent referral usage if referrer has not completed their profile
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



    @Override
    public void completeProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setProfileComplete(true);
        userRepository.save(user);

        // ✅ Check if the user was referred by someone
        if (user.getReferrer() != null) {
            User referrer = user.getReferrer();

            // ✅ If both referrer & referred user have completed profiles, mark referral as complete
            if (referrer.isProfileComplete() && user.isProfileComplete()) {
                user.setReferralComplete(true);
                userRepository.save(user);
                System.out.println("Referral marked as successful for: " + user.getEmail());
            }
        }

        // ✅ Now check if this user has referred others
        List<User> referredUsers = userRepository.findByReferrer(user);
        for (User referredUser : referredUsers) {
            if (referredUser.isProfileComplete() && user.isProfileComplete()) {
                referredUser.setReferralComplete(true);
                userRepository.save(referredUser);
                System.out.println("Referral marked as successful for: " + referredUser.getEmail());
            }
        }
    }

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


    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserMapper::toDTO);
    }

    @Override
    public Optional<UserDTO> findByReferralCode(String referralCode) {
        return userRepository.findByReferralCode(referralCode).map(UserMapper::toDTO);
    }

    private String generateReferralCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
