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
                return;
            }
            user.get().getUsersFavoriteCryptocurrencies().add(favoriteCryptocurrency);
            Optional<Cryptocurrency> cryptocurrency = cryptocurrencyRepository.findById(favoriteCryptocrurrencyDTO.cryptoId);
            if (cryptocurrency.isEmpty()) {
                return;
            }
            cryptocurrency.get().getUsersFavoriteCryptocurrencies().add(favoriteCryptocurrency);
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
            return;
        }
        for (FavoriteCryptocurrency usersFavoriteCryptocurrency : user.get().getUsersFavoriteCryptocurrencies()) {
            if (usersFavoriteCryptocurrency.getCryptoName().equals(favoriteCryptocrurrencyDTO.cryptoId)) {
                usersFavoriteCryptocurrency.setDesiredBuyingPrice(favoriteCryptocrurrencyDTO.desiredBuyingPrice);
                usersFavoriteCryptocurrency.setDesiredSellingPrice(favoriteCryptocrurrencyDTO.desiredSellingPrice);
                userRepository.save(user.get());
                return;
            }
        }
        throw new IllegalStateException("not found");
    }

    public void deleteFavoriteCryptocurrency(String cryptoId) {
        Optional<User> user = userRepository.findByUsername(authenticationFacade.getAuthentication().getName());
        if (user.isEmpty()) {
            return;
        }
        FavoriteCryptocurrency deletedFavoriteCryptocurrency = null;
        for (FavoriteCryptocurrency usersFavoriteCryptocurrency : user.get().getUsersFavoriteCryptocurrencies()) {
            if (usersFavoriteCryptocurrency.getCryptoName().equals(cryptoId)) {
                deletedFavoriteCryptocurrency = usersFavoriteCryptocurrency;
                break;
            }
        }
        if (deletedFavoriteCryptocurrency != null) {
            user.get().getUsersFavoriteCryptocurrencies().remove(deletedFavoriteCryptocurrency);
            userRepository.save(user.get());
            return;
        }
        throw new IllegalStateException("not found");
    }
}
