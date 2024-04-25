package com.cashrich.entity;

import java.util.Map;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "coin_dtls")
@Data
public class CoinData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "Id")
    private User user;

    @OneToMany(mappedBy = "coinData", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "coinName")
    private Map<String, CoinInfoEntity> data;
  
 
}
