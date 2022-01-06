package com.example.cryptotracker.domain;

public interface SmsSender {
    void sendSms(SmsRequest smsRequest);
}
