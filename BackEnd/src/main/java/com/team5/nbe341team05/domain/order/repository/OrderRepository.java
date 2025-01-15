package com.team5.nbe341team05.domain.order.repository;

import com.team5.nbe341team05.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository <Order, Long> {
    Optional<List<Order>> findByEmail(String email);

    @Query("SELECT o FROM Order o WHERE o.email = :email AND o.id = :id")
    Optional<Order> findByEmailAndId(@Param("email") String email, @Param("id") Long id);
    List<Order> findAllByDeliveryStatusFalse();
}
