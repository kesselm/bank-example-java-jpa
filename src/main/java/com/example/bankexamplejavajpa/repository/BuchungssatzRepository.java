package com.example.bankexamplejavajpa.repository;

import com.example.bankexamplejavajpa.entities.Buchungssatz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuchungssatzRepository extends JpaRepository<Buchungssatz, Long> {
}
