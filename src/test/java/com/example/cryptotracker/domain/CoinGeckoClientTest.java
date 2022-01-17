package com.example.cryptotracker.domain;

import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class CoinGeckoClientTest {
    private HttpClient httpClient = mock(HttpClient.class);
    private HttpRequest httpRequest;
    private HttpResponse httpResponse = mock(HttpResponse.class);
    private CoinGeckoClient coinGeckoClient = new CoinGeckoClient(httpClient, httpRequest);

    @Test
    void getCryptocurrencies_whenReauestSucceed_ThenMapDataToCryptocurrencyDTO() throws IOException, InterruptedException {
        when(httpClient.send(eq(httpRequest), any())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn("[{\n" +
                "        \"id\": \"bitcoin\",\n" +
                "        \"symbol\": \"btc\",\n" +
                "        \"name\": \"Bitcoin\",\n" +
                "        \"image\": \"https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579\",\n" +
                "        \"current_price\": 42878,\n" +
                "        \"market_cap\": 810385825372,\n" +
                "        \"market_cap_rank\": 1,\n" +
                "        \"fully_diluted_valuation\": 899154036968,\n" +
                "        \"total_volume\": null,\n" +
                "        \"high_24h\": 43223,\n" +
                "        \"low_24h\": null,\n" +
                "        \"price_change_24h\": null,\n" +
                "        \"price_change_percentage_24h\": null,\n" +
                "        \"market_cap_change_24h\": null,\n" +
                "        \"market_cap_change_percentage_24h\": null,\n" +
                "        \"circulating_supply\": null,\n" +
                "        \"total_supply\": null,\n" +
                "        \"max_supply\": null,\n" +
                "        \"ath\": null,\n" +
                "        \"ath_change_percentage\": null,\n" +
                "        \"ath_date\": null,\n" +
                "        \"atl\": 67.81,\n" +
                "        \"atl_change_percentage\": null,\n" +
                "        \"atl_date\": null,\n" +
                "        \"roi\": null,\n" +
                "        \"last_updated\": null\n" +
                "    }]");
        List<CryptocurrencyDTO> cryptocurrencyDTOS = new ArrayList<>(coinGeckoClient.getCryptocurrencies());
        assertEquals("bitcoin", cryptocurrencyDTOS.get(0).id);
    }
}