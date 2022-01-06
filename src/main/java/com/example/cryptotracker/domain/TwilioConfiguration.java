package com.example.cryptotracker.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
public class TwilioConfiguration {
    private final String accountSid = "AC6e10a7a7a5b45f546465e62e624386aa";
    private final String authToken = "ae7a7227a4b61849f1459a7cf4e578ef";
    private final String trialNumberl = "+17128478253";
}
