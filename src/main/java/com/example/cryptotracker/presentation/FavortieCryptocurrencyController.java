package com.example.cryptotracker.presentation;

import com.example.cryptotracker.domain.FavoriteCryptocrurrencyDTO;
import com.example.cryptotracker.domain.FavoriteCryptocurrency;
import com.example.cryptotracker.domain.FavoriteCryptocurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/cryptocurrencies/favorites")
@AllArgsConstructor
public class FavortieCryptocurrencyController {
    @Autowired
    private final FavoriteCryptocurrencyService favoriteCryptocurrencyService;

    @GetMapping
    public Set<FavoriteCryptocurrency> listData() {
        return favoriteCryptocurrencyService.listFavoriteCryptocurrencies();
    }

    @PostMapping
    public String loadData(@RequestBody FavoriteCryptocrurrencyDTO favoriteCryptocrurrencyDTO) {
        favoriteCryptocurrencyService.addFavoriteCryptocurrency(favoriteCryptocrurrencyDTO);
        return "added";
    }

    @PutMapping
    public String updateData(@RequestBody FavoriteCryptocrurrencyDTO favoriteCryptocrurrencyDTO) {
        favoriteCryptocurrencyService.updateFavoriteCryptocurrency(favoriteCryptocrurrencyDTO);
        return "updated";
    }

    @DeleteMapping("{cryptoId}")
    public String deleteData(@PathVariable String cryptoId) {
        favoriteCryptocurrencyService.deleteFavoriteCryptocurrency(cryptoId);
        return "deleted";
    }

    // TODO Implement phone sms messaging service with twilio
    // TODO Implement the scheduled methodes
    // TODO Implement email verfication
    // TODO Refactor code to handle response entities
    // TODO Right unit tests
    // TODO Clean up code
}
