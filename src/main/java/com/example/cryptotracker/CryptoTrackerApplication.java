package com.example.cryptotracker;

import com.example.cryptotracker.domain.CryptocurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class CryptoTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoTrackerApplication.class, args);
    }

    @Autowired
    CryptocurrencyService cryptocurrencyService;

    @Scheduled(fixedRateString = "PT5M")
    void loadingJob() {
        cryptocurrencyService.loadData();
    }

}


