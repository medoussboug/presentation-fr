package com.example.cryptotracker.domain;

import lombok.Data;

@Data
public class SmsRequest {
    private final String phoneNumber;
    private final String message;

    public SmsRequest(String phoneNumber, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }
}
