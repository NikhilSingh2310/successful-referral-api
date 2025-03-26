package com.simplifymoney.successfulreferral.service;

import com.simplifymoney.successfulreferral.dto.UserDTO;

import java.io.PrintWriter;
import java.util.Optional;
import java.util.List;

public interface UserService {
    UserDTO signup(String name, String email, String password, String referralCode);
    void completeProfile(Long userId);
    Optional<UserDTO> findByEmail(String email);
    Optional<UserDTO> findByReferralCode(String referralCode);
    List<UserDTO> getReferrals(Long userId);
    void generateReferralReport(PrintWriter writer);

}
