package com.example.cryptotracker.domain;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Transactional
    public void loadData() {
        System.out.println("bsmlah");
        List<CryptocurrencyDTO> loadedCryptocurrencies = coinGeckoClient.getCryptocurrencies();
        for (CryptocurrencyDTO loadedCryptocurrency : loadedCryptocurrencies) {
            Optional<Cryptocurrency> cryptocurrency = cryptocurrencyRepository.findById(loadedCryptocurrency.id);
            if (cryptocurrency.isPresent() && !loadedCryptocurrency.currentPrice.equals(cryptocurrency.get().getCurrentPrice())) {
                Set<FavoriteCryptocurrency> favoriteCryptocurrencies = cryptocurrency.get().getUsersFavoriteCryptocurrencies();
                if (favoriteCryptocurrencies != null && favoriteCryptocurrencies.size() > 0) {
                    for (FavoriteCryptocurrency favoriteCryptocurrency : favoriteCryptocurrencies) {
                        if ((loadedCryptocurrency.currentPrice <= favoriteCryptocurrency.getDesiredBuyingPrice() || loadedCryptocurrency.currentPrice >= favoriteCryptocurrency.getDesiredSellingPrice()) && !favoriteCryptocurrency.getNotified()) {
//                            smsSender.sendSms(
//                                    new SmsRequest(
//                                            favoriteCryptocurrency.getUser()
//                                                    .getPhoneNumber(),
//                                            String.format("Quick Quick!!!!!\n the price of %s has gone to your desired price", favoriteCryptocurrency.getCryptoName())
//                                    )
//                            );
                            System.out.println("==============================================SENT===================================================");
                            favoriteCryptocurrency.setNotified(true);
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

    public void test() {
        System.out.println("==============================TEST TEST================================" + new Date());
        for (FavoriteCryptocurrency favoriteCryptocurrency : favoriteCryptocurrencyRepository.findAll()) {
            favoriteCryptocurrency.setNotified(false);
            favoriteCryptocurrencyRepository.save(favoriteCryptocurrency);
        }
    }
}
