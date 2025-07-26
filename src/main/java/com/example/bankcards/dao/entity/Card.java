package com.example.bankcards.dao.entity;

import com.example.bankcards.dao.enums.CardStatus;
import com.example.bankcards.util.converter.YearMonthConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private String encryptNumber;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private String owner;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    @Convert(converter = YearMonthConverter.class)
    private YearMonth expiryDate;

    @Column(nullable = false)
    private CardStatus status = CardStatus.ACTIVE;

    @DecimalMin(value = "0.00")
    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Card(String encryptNumber, String owner, YearMonth expiryDate, User user) {
        this.encryptNumber = encryptNumber;
        this.owner = owner;
        this.expiryDate = expiryDate;
        this.user = user;
    }

    protected Card() {
    }
}
