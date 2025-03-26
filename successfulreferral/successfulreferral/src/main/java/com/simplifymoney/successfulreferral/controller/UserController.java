package com.simplifymoney.successfulreferral.controller;

import com.simplifymoney.successfulreferral.dto.ProfileCompletionRequest;
import com.simplifymoney.successfulreferral.dto.SignupRequest;
import com.simplifymoney.successfulreferral.dto.UserDTO;
import com.simplifymoney.successfulreferral.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    //  User Signup API
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignupRequest request) {
        UserDTO user = userService.signup(request.getName(), request.getEmail(), request.getPassword(), request.getReferralCode());
        return ResponseEntity.ok(user);
    }

    //  Profile Completion API
    @PostMapping("/profile/complete")
    public ResponseEntity<String> completeProfile(@RequestBody ProfileCompletionRequest request) {
        userService.completeProfile(request.getUserId());
        return ResponseEntity.ok("Profile completed successfully!");
    }

    //  Find User by Email API
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        Optional<UserDTO> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{referrerId}/referrals")
    public ResponseEntity<List<UserDTO>> getReferrals(@PathVariable Long referrerId) {
        List<UserDTO> referrals = userService.getReferrals(referrerId);
        return ResponseEntity.ok(referrals);
    }




}
