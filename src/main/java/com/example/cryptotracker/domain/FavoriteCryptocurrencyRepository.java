package com.example.cryptotracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteCryptocurrencyRepository extends JpaRepository<FavoriteCryptocurrency, Long> {
    Optional<FavoriteCryptocurrency> findByCryptoName(String cryptoName);
}
