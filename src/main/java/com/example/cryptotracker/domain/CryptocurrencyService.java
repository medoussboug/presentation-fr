package com.example.cryptotracker.domain;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CryptocurrencyService {
    @Autowired
    private final CryptocurrencyMapper cryptocurrencyMapper;

    @Autowired
    private final CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    private final CoinGeckoClient coinGeckoClient;

    public void loadData() {
        List<CryptocurrencyDTO> loadedCryptocurrencies = coinGeckoClient.getCryptocurrencies();
        for (CryptocurrencyDTO loadedCryptocurrency : loadedCryptocurrencies) {
            cryptocurrencyRepository.save(cryptocurrencyMapper.mapToEntity(loadedCryptocurrency));
        }
    }

    public List<Cryptocurrency> listData() {
        return cryptocurrencyRepository.findAll();
    }
}
