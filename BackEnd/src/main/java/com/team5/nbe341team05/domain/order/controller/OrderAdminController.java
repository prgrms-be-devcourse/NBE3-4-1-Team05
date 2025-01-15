package com.team5.nbe341team05.domain.order.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.order.dto.OrderResponseDto;
import com.team5.nbe341team05.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class OrderAdminController {

    private final OrderService orderService;

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseMessage<OrderResponseDto> cancelOrder(@PathVariable long id) {
        orderService.cancelAdminOrder(id);
        return new ResponseMessage<>(
                "주문이 성공적으로 취소되었습니다.",
                String.valueOf(HttpStatus.OK.value()),
                null);
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> getOrderListForAdmin(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir
    ) {
        Page<OrderResponseDto> pagedOrders = orderService.getPagedOrders(page, size, sortBy, sortDir);
        return ResponseEntity.ok(pagedOrders);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseMessage<OrderResponseDto> getOrderDetailById(@PathVariable long id) {
        OrderResponseDto order = this.orderService.getOrderDetailsForAdmin(id);
        return new ResponseMessage<>("주문 상세 조회 성공", String.valueOf(HttpStatus.OK.value()), order);
    }
}
