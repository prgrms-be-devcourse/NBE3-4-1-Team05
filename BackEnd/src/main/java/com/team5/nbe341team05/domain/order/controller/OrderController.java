package com.team5.nbe341team05.domain.order.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.order.dto.OrderDto;
import com.team5.nbe341team05.domain.order.dto.OrderResponseDto;
import com.team5.nbe341team05.domain.order.entity.Order;
import com.team5.nbe341team05.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseMessage<OrderResponseDto> createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.createOrder(orderDto);

        return new ResponseMessage<>(
                "주문이 성공적으로 생성되었습니다.",
                String.valueOf(HttpStatus.OK.value()),
                new OrderResponseDto(order)
        );
    }
}
