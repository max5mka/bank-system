package com.example.bankcards.dao.repository;

import com.example.bankcards.dao.entity.BankQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankQueryRepository extends JpaRepository<BankQuery, Long> {
}
