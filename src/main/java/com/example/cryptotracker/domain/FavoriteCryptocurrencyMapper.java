package com.example.cryptotracker.domain;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class FavoriteCryptocurrencyMapper {

    @Autowired
    private final CryptocurrencyRepository cryptocurrencyRepository;

    FavoriteCryptocurrency mapToEntity(FavoriteCryptocrurrencyDTO favoriteCryptocrurrencyDTO) {
        Optional<Cryptocurrency> cryptocurrency = cryptocurrencyRepository.findById(favoriteCryptocrurrencyDTO.cryptoId);
        return cryptocurrency.map(value -> FavoriteCryptocurrency.builder()
                .cryptoName(value.getId())
                .cryptoPrice(value.getCurrentPrice())
                .desiredBuyingPrice(favoriteCryptocrurrencyDTO.desiredBuyingPrice)
                .desiredSellingPrice(favoriteCryptocrurrencyDTO.desiredSellingPrice)
                .build())
                .orElse(null);
    }
}
