package com.example.kantoronline.entities;

import com.example.kantoronline.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "CURRENCIES")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private CurrencyType currencyType;
    @Column(precision = 19, scale = 4)
    private BigDecimal balance;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
