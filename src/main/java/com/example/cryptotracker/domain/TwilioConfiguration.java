package com.example.cryptotracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class TwilioConfiguration {
    private final String accountSid = "AC6e10a7a7a5b45f546465e62e624386aa";
    private final String authToken = "9640b249ec2ef35637fcca129fee12cb\n";
    private final String trialNumberl = "+17128478253";
}
