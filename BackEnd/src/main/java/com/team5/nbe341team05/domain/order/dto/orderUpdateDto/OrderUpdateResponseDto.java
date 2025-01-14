package com.team5.nbe341team05.domain.order.dto.orderUpdateDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateResponseDto {
    private Long orderId;     // 수정된 주문의 ID
    private String message;
}