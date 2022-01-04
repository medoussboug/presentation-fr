package com.example.cryptotracker.domain;

import lombok.AllArgsConstructor;

import javax.persistence.Column;

@AllArgsConstructor
public class FavoriteCryptocrurrencyDTO {
    public final String cryptoId;
    public final Double desiredSellingPrice;
    public final Double desiredBuyingPrice;
}
