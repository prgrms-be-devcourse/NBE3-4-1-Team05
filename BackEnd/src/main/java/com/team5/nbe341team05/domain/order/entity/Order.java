package com.team5.nbe341team05.domain.order.entity;


import com.team5.nbe341team05.common.jpa.entity.BaseTime;
import com.team5.nbe341team05.domain.orderMenu.entity.OrderMenu;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"order\"")
public class Order extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;                 // 주문 번호

    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;            // 고객 이메일

    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;          // 고객 주소

    private boolean deliveryStatus;  // 배송 상태

    private int totalPrice;          // 총 주문 가격

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderMenu> orderMenus = new ArrayList<>();

    public void updateOrder(String email, String address) {
        this.email = email;
        this.address = address;
    }

    public void updateStatus(boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        this.orderMenus.add(orderMenu);
    }

    public void calculateTotalPrice() {
        this.totalPrice = this.orderMenus.stream()
                .mapToInt(orderMenu -> orderMenu.getMenu().getPrice() * orderMenu.getQuantity())
                .sum();
    }
}
