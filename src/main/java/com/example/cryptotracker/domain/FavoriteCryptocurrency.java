package com.example.cryptotracker.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;


@Table(name = "Favorite_Cryptocurrency")
@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

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
    private Boolean notified;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @ManyToOne
    @JoinColumn(name = "crypto_id")
    @JsonIgnore
    private Cryptocurrency cryptocurrency;


    @Override
    public String toString() {
        return "\nFavoriteCryptocurrency{" +
                "\nfavoriteCryptoId=" + favoriteCryptoId +
                "\n, cryptoName='" + cryptoName + '\'' +
                "\n, cryptoPrice=" + cryptoPrice +
                "\n, desiredSellingPrice=" + desiredSellingPrice +
                "\n, desiredBuyingPrice=" + desiredBuyingPrice +
                "\n, user=" + user +
                "\n, cryptocurrency=" + cryptocurrency +
                '}';
    }
}
