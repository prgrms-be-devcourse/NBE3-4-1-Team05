package com.team5.nbe341team05.domain.order.dto;

import com.team5.nbe341team05.domain.order.entity.Order;
import com.team5.nbe341team05.domain.orderMenu.Dto.OrderMenuDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private String email;
    private String address;
    private LocalDateTime order_time;
    private LocalDateTime modify_time;
    private int totalPrice;
    private boolean deliveryStatus;
    private List<OrderMenuDto> omlist;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.email = order.getEmail();
        this.address = order.getAddress();
        if (order.getModifyDate() != null && !order.getModifyDate().equals(order.getCreateDate())) {
            this.order_time = order.getModifyDate(); // 수정된 경우
        } else {
            this.order_time = order.getCreateDate(); // 수정되지 않은 경우
        }
        this.totalPrice = order.getTotalPrice();
        this.deliveryStatus = order.isDeliveryStatus();
        this.omlist = order.getOrderMenus().stream()
                .map(orderMenu -> new OrderMenuDto(
                        orderMenu.getMenu().getId(),
                        orderMenu.getMenu().getProductName(),
                        orderMenu.getQuantity())).toList();
    }
}
