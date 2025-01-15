package com.team5.nbe341team05.domain.order.repository;

import com.team5.nbe341team05.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository <Order, Long> {
    List<Order> findByEmail(String email);
    Page<Order> findAll(Pageable pageable);
}
