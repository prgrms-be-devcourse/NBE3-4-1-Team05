package com.team5.nbe341team05.domain.order.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.order.dto.OrderDto;
import com.team5.nbe341team05.domain.order.dto.OrderResponseDto;
import com.team5.nbe341team05.domain.order.entity.Order;
import com.team5.nbe341team05.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{email}")
    public ResponseMessage<List<OrderResponseDto>> getOrdersByEmail(@PathVariable String email) {
        List<OrderResponseDto> orders = orderService.getOrdersByEmail(email);
        return new ResponseMessage<>(
                "주문 목록 조회 성공",
                String.valueOf(HttpStatus.OK.value()),
                orders
        );
    }
}
