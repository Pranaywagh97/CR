package com.cashrich.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name= "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    
    @Column(name="first_name")
    private String fName;
    
    @Column(name="last_name")
    private String lName;
    
    @Column(name="email", unique=true)
    private String email;
    
    @Column(name="Phone_number")
    private String phoneNo;
    
    @Column(name="user_name", unique=true)
    private String userName;
    
    @Column(name="password")
    private String password;
    
    @Column(name = "api_response")
    private String apiResponse;
    
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CoinData coinData;

}
