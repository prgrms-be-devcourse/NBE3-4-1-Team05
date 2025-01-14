package com.team5.nbe341team05.domain.order.repository;

import com.team5.nbe341team05.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Order, Long> {
}
