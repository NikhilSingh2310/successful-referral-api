package com.simplifymoney.successfulreferral.controller;

import com.simplifymoney.successfulreferral.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/api/referrals")  // ✅ Changed base path
public class ReferralController {

    @Autowired
    private UserService userService;

    @GetMapping("/report")  // ✅ Correct mapping
    public void generateReferralReport(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=referral_report.csv");

        PrintWriter writer = response.getWriter();
        userService.generateReferralReport(writer);
    }
}
