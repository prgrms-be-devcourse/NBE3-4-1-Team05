package com.team5.nbe341team05.domain.orderMenu.repository;

import com.team5.nbe341team05.domain.orderMenu.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
