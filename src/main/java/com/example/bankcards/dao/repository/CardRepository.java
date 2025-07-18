package com.example.bankcards.dao.repository;

import com.example.bankcards.dao.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByUserId(Long userId);
}
