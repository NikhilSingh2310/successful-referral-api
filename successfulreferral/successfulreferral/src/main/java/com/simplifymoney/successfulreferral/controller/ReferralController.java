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
@RequestMapping("/api/referrals")
public class ReferralController {

    @Autowired
    private UserService userService;

    /**
     * Generates and returns a CSV report of all user referrals.
     * The report contains details of referrers, referred users, and referral status.
     * The generated CSV file is automatically downloaded upon request.
     */
    @GetMapping("/report")
    public void generateReferralReport(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=referral_report.csv");

        PrintWriter writer = response.getWriter();
        userService.generateReferralReport(writer);
    }
}
