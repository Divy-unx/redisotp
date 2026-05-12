package com.divyanshu.redisotp.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OTPService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // Generate OTP
    public String generateOTP(String phone) {

        Random random = new Random();

        int otp = 1000 + random.nextInt(9000);

        String otpValue = String.valueOf(otp);

        // Store OTP in Redis for 60 sec
        redisTemplate.opsForValue()
                .set(phone, otpValue, 60, TimeUnit.SECONDS);

        return otpValue;
    }

    // Verify OTP
    public String verifyOTP(String phone, String userOtp) {

        String storedOtp =
                redisTemplate.opsForValue().get(phone);

        if (storedOtp == null) {
            return "OTP Expired";
        }

        if (storedOtp.equals(userOtp)) {

            redisTemplate.delete(phone);

            return "OTP Verified Successfully";
        }

        return "Invalid OTP";
    }
}