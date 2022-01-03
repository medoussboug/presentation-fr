package com.example.cryptotracker.presentation;

import com.example.cryptotracker.domain.Cryptocurrency;
import com.example.cryptotracker.domain.CryptocurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/cryptocurrencies")
@AllArgsConstructor
public class CryptocurrenciesController {

    @Autowired
    private final CryptocurrencyService cryptocurrencyService;

    @GetMapping
    public List<Cryptocurrency> listData() {
        return cryptocurrencyService.listData();
    }

    @PostMapping
    public String loadData() {
        try {
            cryptocurrencyService.loadData();
        } catch (Exception e) {
            return e.getMessage();
        }
        return "loaded";
    }
}
