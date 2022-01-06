package com.example.cryptotracker.domain;

import com.example.cryptotracker.authentication.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class FavoriteCryptocurrencyService {
    @Autowired
    private final FavoriteCryptocurrencyMapper favoriteCryptocurrencyMapper;

    @Autowired
    private final AuthenticationFacade authenticationFacade;

    @Autowired
    private final FavoriteCryptocurrencyRepository favoriteCryptocurrencyRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CryptocurrencyRepository cryptocurrencyRepository;

    public void addFavoriteCryptocurrency(FavoriteCryptocrurrencyDTO favoriteCryptocrurrencyDTO) {

        try {
            favoriteCryptocurrencyRepository.findByCryptoName(favoriteCryptocrurrencyDTO.cryptoId).isPresent();
        } catch (Exception e) {
            throw new IllegalStateException("already exists");
        }
        FavoriteCryptocurrency favoriteCryptocurrency = favoriteCryptocurrencyMapper.mapToEntity(favoriteCryptocrurrencyDTO);
        if (favoriteCryptocurrency != null) {
            Optional<User> user = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
            if (user.isEmpty()) {
                throw new IllegalStateException("user not found");
            }
            user.get().getUsersFavoriteCryptocurrencies().add(favoriteCryptocurrency);
            favoriteCryptocurrency.setUser(user.get());
            Optional<Cryptocurrency> cryptocurrency = cryptocurrencyRepository.findById(favoriteCryptocrurrencyDTO.cryptoId);
            if (cryptocurrency.isEmpty()) {
                throw new IllegalStateException("cryptocurrency not found");
            }
            if (cryptocurrency.get().getCurrentPrice() > favoriteCryptocrurrencyDTO.desiredSellingPrice || cryptocurrency.get().getCurrentPrice() < favoriteCryptocrurrencyDTO.desiredBuyingPrice) {
                throw new IllegalArgumentException("fixe desired prices");
            }
            cryptocurrency.get().getUsersFavoriteCryptocurrencies().add(favoriteCryptocurrency);
            favoriteCryptocurrency.setCryptocurrency(cryptocurrency.get());
            favoriteCryptocurrencyRepository.save(favoriteCryptocurrency);
            userRepository.save(user.get());
            cryptocurrencyRepository.save(cryptocurrency.get());
            }
    }

    public Set<FavoriteCryptocurrency> listFavoriteCryptocurrencies() {
        Optional<User> user = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
        return user.get().getUsersFavoriteCryptocurrencies();
    }

    public void updateFavoriteCryptocurrency(FavoriteCryptocrurrencyDTO favoriteCryptocrurrencyDTO) {
        Optional<User> user = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
        if (user.isEmpty()) {
            throw new IllegalStateException("user not found");
        }
        Optional<FavoriteCryptocurrency> favoriteCryptocurrency = favoriteCryptocurrencyRepository.findByUserAndCryptoName(user.get(), favoriteCryptocrurrencyDTO.cryptoId);
        if (favoriteCryptocurrency.isEmpty()) {
            throw new IllegalStateException("not found");
        }
        if (favoriteCryptocurrency.get().getCryptoPrice() > favoriteCryptocrurrencyDTO.desiredSellingPrice || favoriteCryptocurrency.get().getCryptoPrice() < favoriteCryptocrurrencyDTO.desiredBuyingPrice) {
            throw new IllegalArgumentException("fixe desired prices");
        }
        favoriteCryptocurrency.get().setDesiredSellingPrice(favoriteCryptocrurrencyDTO.desiredSellingPrice);
        favoriteCryptocurrency.get().setDesiredBuyingPrice(favoriteCryptocrurrencyDTO.desiredBuyingPrice);
        favoriteCryptocurrencyRepository.save(favoriteCryptocurrency.get());
    }

    public void deleteFavoriteCryptocurrency(String cryptoId) {

        Optional<User> user = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
        if (user.isEmpty()) {
            throw new IllegalStateException("user not found");
        }
        Optional<FavoriteCryptocurrency> favoriteCryptocurrency = favoriteCryptocurrencyRepository.findByUserAndCryptoName(user.get(), cryptoId);
        if (favoriteCryptocurrency.isEmpty()) {
            throw new IllegalStateException("not found");
        }
        favoriteCryptocurrencyRepository.delete(favoriteCryptocurrency.get());
    }
}
