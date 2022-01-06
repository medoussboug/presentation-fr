package com.example.cryptotracker.domain;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CryptocurrencyService {
    @Autowired
    private final SmsSender smsSender;

    @Autowired
    private final CryptocurrencyMapper cryptocurrencyMapper;

    @Autowired
    private final CryptocurrencyRepository cryptocurrencyRepository;

    @Autowired
    private final FavoriteCryptocurrencyRepository favoriteCryptocurrencyRepository;

    @Autowired
    private final CoinGeckoClient coinGeckoClient;

    public void loadData() {
        System.out.println("bsmlah");
        List<CryptocurrencyDTO> loadedCryptocurrencies = coinGeckoClient.getCryptocurrencies();
        for (CryptocurrencyDTO loadedCryptocurrency : loadedCryptocurrencies) {
            Optional<Cryptocurrency> cryptocurrency = cryptocurrencyRepository.findById(loadedCryptocurrency.id);
            if (cryptocurrency.isPresent() && !loadedCryptocurrency.currentPrice.equals(cryptocurrency.get().getCurrentPrice())) {
                Set<FavoriteCryptocurrency> favoriteCryptocurrencies = cryptocurrency.get().getUsersFavoriteCryptocurrencies();
                if (favoriteCryptocurrencies.size() > 0) {
                    for (FavoriteCryptocurrency favoriteCryptocurrency : favoriteCryptocurrencies) {
                        if (loadedCryptocurrency.currentPrice <= favoriteCryptocurrency.getDesiredBuyingPrice() || loadedCryptocurrency.currentPrice >= favoriteCryptocurrency.getDesiredSellingPrice()) {
                            smsSender.sendSms(
                                    new SmsRequest(
                                            favoriteCryptocurrency.getUser()
                                                    .getPhoneNumber(),
                                            String.format("3lach nta zaml", favoriteCryptocurrency.getCryptoName())
                                    )
                            );
                        }
                        favoriteCryptocurrency.setCryptoPrice(loadedCryptocurrency.currentPrice);
                        favoriteCryptocurrencyRepository.save(favoriteCryptocurrency);
                    }
                }

            }
            cryptocurrencyRepository.save(cryptocurrencyMapper.mapToEntity(loadedCryptocurrency));

        }
    }

    public List<Cryptocurrency> listData() {
        return cryptocurrencyRepository.findAll();
    }
}
