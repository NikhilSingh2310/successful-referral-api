package com.simplifymoney.successfulreferral.controller;

import com.simplifymoney.successfulreferral.dto.ProfileCompletionRequest;
import com.simplifymoney.successfulreferral.dto.SignupRequest;
import com.simplifymoney.successfulreferral.dto.UserDTO;
import com.simplifymoney.successfulreferral.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Handles user signup, generates a unique referral code,
     * and links the referrer if a referral code is provided.
     */
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignupRequest request) {
        UserDTO user = userService.signup(request.getName(), request.getEmail(), request.getPassword(), request.getReferralCode());
        return ResponseEntity.ok(user);
    }

    /**
     * Marks a user's profile as complete and updates referral status accordingly.
     */
    @PostMapping("/profile/complete")
    public ResponseEntity<String> completeProfile(@RequestBody ProfileCompletionRequest request) {
        userService.completeProfile(request.getUserId());
        return ResponseEntity.ok("Profile completed successfully!");
    }

    /**
     * Retrieves user details by email.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        Optional<UserDTO> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Fetches all users referred by a specific referrer.
     */
    @GetMapping("/{referrerId}/referrals")
    public ResponseEntity<List<UserDTO>> getReferrals(@PathVariable Long referrerId) {
        List<UserDTO> referrals = userService.getReferrals(referrerId);
        return ResponseEntity.ok(referrals);
    }
}
