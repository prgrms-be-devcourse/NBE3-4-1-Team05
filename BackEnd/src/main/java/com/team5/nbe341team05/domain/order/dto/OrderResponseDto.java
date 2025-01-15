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
    private String email;
    private String address;
    private LocalDateTime order_time;
    private int totalPrice;
    private boolean deliveryStatus;
    private List<OrderMenuDto> omlist;

    public OrderResponseDto(Order order) {
        this.email = order.getEmail();
        this.address = order.getAddress();
        this.order_time = order.getOrderTime();
        this.totalPrice = order.getTotalPrice();
        this.deliveryStatus = order.isDeliveryStatus();
        this.omlist = order.getOrderMenus().stream()
                .map(orderMenu -> new OrderMenuDto(
                        orderMenu.getMenu().getProductName(),
                        orderMenu.getQuantity())).toList();
    }
}
