package com.wrogn.task.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "refresh-tokens")
public class RefreshTokenEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String token;

    private String email;

    private Date expiryDate;
}
