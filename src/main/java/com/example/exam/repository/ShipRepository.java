package com.example.exam.repository;

import com.example.exam.model.entity.Ship;
import com.example.exam.model.view.ShipViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {
    @Query("SELECT s from Ship s where s.user.id = ?1 ")
    Collection<Ship> findAllByUserID(Long id);

    @Query("SELECT s from Ship s where s.user.id <> ?1 ")
    Collection<Ship> findAllByNotUserID(Long id);

    List<Ship> findAllByOrderById();
}
