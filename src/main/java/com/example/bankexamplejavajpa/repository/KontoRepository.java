package com.example.bankexamplejavajpa.repository;

import com.example.bankexamplejavajpa.entities.Konto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KontoRepository extends JpaRepository<Konto, Long> {
}
