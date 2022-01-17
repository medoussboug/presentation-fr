package com.example.cryptotracker.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FavoriteCryptocurrencyMapperTest {
    private CryptocurrencyRepository cryptocurrencyRepository = Mockito.mock(CryptocurrencyRepository.class);
    private FavoriteCryptocurrencyMapper favoriteCryptocurrencyMapper = new FavoriteCryptocurrencyMapper(cryptocurrencyRepository);

    @Test
    void mapToEntity_whenGettingFavoriteCryptoDTO_thenMapToEntity() {
        FavoriteCryptocrurrencyDTO favoriteCryptocrurrencyDTO = new FavoriteCryptocrurrencyDTO("bitcoin", 100.0, 10.0);
        when(cryptocurrencyRepository.findById(favoriteCryptocrurrencyDTO.cryptoId)).thenReturn(
                Optional.ofNullable(Cryptocurrency.builder()
                        .id("bitcoin")
                        .currentPrice(10000.0)
                        .build())
        );
        FavoriteCryptocurrency favoriteCryptocurrency = favoriteCryptocurrencyMapper.mapToEntity(favoriteCryptocrurrencyDTO);
        assertEquals(favoriteCryptocurrency.getCryptoName(), "bitcoin");
    }
}