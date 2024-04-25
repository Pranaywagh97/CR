package com.cashrich.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coin_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String coinName;

    @ManyToOne
    @JoinColumn(name = "coin_data_id")
    private CoinData coinData;

    private String price;
    private String marketCap;
    private String volume24h;
    
    public CoinInfoEntity(String coinName, String price, String marketCap, String volume24h) {
        this.coinName = coinName;
        this.price = price;
        this.marketCap = marketCap;
        this.volume24h = volume24h;
    }
  

}

