package com.team5.nbe341team05.domain.cartMenu.repository;

import com.team5.nbe341team05.domain.cartMenu.entity.CartMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {
}
