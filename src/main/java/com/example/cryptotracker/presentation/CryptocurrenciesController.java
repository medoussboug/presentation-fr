package com.example.cryptotracker.presentation;

import com.example.cryptotracker.domain.CoinGeckoClient;
import com.example.cryptotracker.domain.CryptocurrencyDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
public class CryptocurrenciesController {

    @Autowired
    private final CoinGeckoClient coinGeckoClient;

    @GetMapping
    public List<CryptocurrencyDTO> listData() {
        return coinGeckoClient.getCryptocurrencies();
    }
}
