package com.team5.nbe341team05.domain.order.repository;

import com.team5.nbe341team05.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository <Order, Long> {
    Optional<Order> findById(Long id);
    Page<Order> findAll(Pageable pageable);
}
