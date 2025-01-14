package com.team5.nbe341team05.domain.cart.repository;

import com.team5.nbe341team05.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
