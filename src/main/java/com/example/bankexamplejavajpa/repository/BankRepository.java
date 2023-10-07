package com.example.bankexamplejavajpa.repository;

import com.example.bankexamplejavajpa.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
}
