package com.team5.nbe341team05.domain.order.entity;


import com.team5.nbe341team05.domain.cart.entity.Cart;
import com.team5.nbe341team05.domain.orderMenu.entity.OrderMenu;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;                 // 주문 번호

    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;            // 고객 이메일

    private LocalDateTime orderTime; // 주문 시간

    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;          // 고객 주소

    private boolean deliveryStatus;  // 배송 상태

    private int totalPrice;          // 총 주문 가격

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    public void updateOrder(String email, String address,int totalPrice) {
        this.email = email;
        this.address = address;
        this.totalPrice = totalPrice;
    }

    public void updateStatus(boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        this.orderMenus.add(orderMenu);
        orderMenu.setOrder(this);
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
