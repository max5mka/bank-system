package com.example.bankcards.dao.entity;

import com.example.bankcards.dao.enums.BankQueryStatus;
import com.example.bankcards.dao.enums.BankQueryType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class BankQuery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private Long userId;

    private Long cardId;

    @Setter(AccessLevel.NONE)
    @Column(nullable = false)
    private BankQueryType bankQueryType;

    @Column(nullable = false)
    private BankQueryStatus bankQueryStatus;

    public BankQuery(
            Long userId,
            Long cardId,
            BankQueryType bankQueryType,
            BankQueryStatus bankQueryStatus
    ) {
        this.userId = userId;
        this.cardId = cardId;
        this.bankQueryType = bankQueryType;
        this.bankQueryStatus = bankQueryStatus;
    }

    protected BankQuery() {

    }
}
