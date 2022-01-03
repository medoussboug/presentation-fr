package com.example.cryptotracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteCryptocurrencyRepository extends JpaRepository<FavoriteCryptocurrency, Long> {
}
