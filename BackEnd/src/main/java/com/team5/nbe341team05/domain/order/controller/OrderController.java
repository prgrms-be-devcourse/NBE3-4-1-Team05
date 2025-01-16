package com.team5.nbe341team05.domain.order.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.order.dto.OrderDto;
import com.team5.nbe341team05.domain.order.dto.OrderResponseDto;
import com.team5.nbe341team05.domain.order.dto.OrderUpdateRequestDto;
import com.team5.nbe341team05.domain.order.entity.Order;
import com.team5.nbe341team05.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order", description = "Order API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다")
    @ApiResponse(responseCode = "201", description = "성공")
    @ApiResponse(responseCode = "400", description = "실패")
    public ResponseMessage<OrderResponseDto> createOrder(@RequestBody OrderDto orderDto) {
        Order order = orderService.createOrder(orderDto);

        return new ResponseMessage<>(
                "%d번 주문이 성공적으로 생성되었습니다.".formatted(order.getId()),
                String.valueOf(HttpStatus.CREATED.value()),
                new OrderResponseDto(order)
        );
    }

    @GetMapping("/{email}")
    @Operation(summary = "주문 리스트 조회", description = "주문 리스트를 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
    public ResponseMessage<List<OrderResponseDto>> getOrdersByEmail(@PathVariable String email) {
        List<OrderResponseDto> orders = orderService.getOrdersByEmail(email);
        return new ResponseMessage<>(
                "주문 목록 조회 성공",
                String.valueOf(HttpStatus.OK.value()),
                orders
        );
    }

    @GetMapping("/{email}/{id}")
    @Operation(summary = "주문 상세 조회", description = "주문 상세 내역을 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
    public ResponseMessage<OrderResponseDto> getOrderDetails(@PathVariable String email, @PathVariable Long id) {
        OrderResponseDto order = orderService.getOrderDetails(email, id);
        return new ResponseMessage<>(
                "주문 상세 조회 성공",
                String.valueOf(HttpStatus.OK.value()),
                order
        );
    }

    @PutMapping("/{email}/{id}")
    @Operation(summary = "주문 수정", description = "주문 내역을 수정합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
    public ResponseMessage<OrderResponseDto> updateOrder(@PathVariable String email, @PathVariable Long id, @RequestBody OrderUpdateRequestDto updateRequestDto) {
        OrderResponseDto updatedOrder = orderService.updateOrder(email, id, updateRequestDto);
        return new ResponseMessage<>(
                "%d번 주문이 성공적으로 수정되었습니다.".formatted(updatedOrder.getId()),
                String.valueOf(HttpStatus.OK.value()),
                updatedOrder
        );
    }

    @DeleteMapping("/{email}/{id}")
    @Operation(summary = "주문 취소", description = "주문을 취소합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
    public ResponseMessage<Void> cancelOrder(@PathVariable String email, @PathVariable Long id) {
        orderService.cancelOrder(email, id);
        return new ResponseMessage<>(
                "주문이 성공적으로 취소되었습니다.",
                String.valueOf(HttpStatus.OK.value()),
                null
        );
    }
}
