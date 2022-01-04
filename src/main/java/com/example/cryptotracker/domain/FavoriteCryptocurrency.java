package com.example.cryptotracker.domain;


import lombok.*;

import javax.persistence.*;


@Table(name = "Favorite_Cryptocurrency")
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FavoriteCryptocurrency {
    @SequenceGenerator(
            name = "favorite_cryptocurrency_sequence",
            sequenceName = "favorite_cryptocurrency_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "favorite_cryptocurrency_sequence"
    )
    @Id
    @Column(name = "favorite_id")
    private Long favoriteCryptoId;
    @Column(name = "crypto_name")
    private String cryptoName;
    @Column(name = "crypto_price")
    private Double cryptoPrice;
    @Column(name = "desired_selling_price")
    private Double desiredSellingPrice;
    @Column(name = "desired_buying_price")
    private Double desiredBuyingPrice;

}
