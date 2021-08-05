package com.example.demo.repository;

import org.springframework.stereotype.Repository;
import java.util.List;

import com.example.demo.Models.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByNameContaining(String name);
}