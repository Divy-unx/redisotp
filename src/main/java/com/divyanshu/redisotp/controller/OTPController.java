package com.divyanshu.redisotp.controller;

import com.divyanshu.redisotp.service.OTPService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class OTPController {

    @Autowired
    private OTPService otpService;

    // Generate OTP
    @PostMapping("/generate")
    public String generateOTP(
            @RequestParam String phone) {

        return otpService.generateOTP(phone);
    }

    // Verify OTP
    @PostMapping("/verify")
    public String verifyOTP(
            @RequestParam String phone,
            @RequestParam String otp) {

        return otpService.verifyOTP(phone, otp);
    }
}