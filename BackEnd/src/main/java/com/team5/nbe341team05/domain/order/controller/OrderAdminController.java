package com.team5.nbe341team05.domain.order.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.order.dto.OrderResponseDto;
import com.team5.nbe341team05.domain.order.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class OrderAdminController {

    private OrderService orderService;

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseMessage<OrderResponseDto> cancelOrder(@PathVariable long id) {
        orderService.cancelAdminOrder(id);
        return new ResponseMessage<>(
                "주문이 성공적으로 취소되었습니다.",
                String.valueOf(HttpStatus.OK.value()),
                null);
    }

    @Transactional
    @GetMapping()
    public ResponseEntity<Page<OrderResponseDto>> getOrderListForAdmin( // 추후에 가능하면 이메일 별 검색 기능 추가
                                                                        @RequestParam(defaultValue = "0") int page, // 0-based index
                                                                        @RequestParam(defaultValue = "10") int size,
                                                                        @RequestParam(defaultValue = "id") String sortBy,
                                                                        @RequestParam(defaultValue = "DESC") String sortDir
    ) {
        Page<OrderResponseDto> pagedOrders = orderService.getPagedOrders(page, size, sortBy, sortDir);
        return ResponseEntity.ok(pagedOrders);
    }

    @Transactional
    @GetMapping("/{id}")
    public ResponseMessage<OrderResponseDto> getOrderDetailById(@PathVariable long id) {
        OrderResponseDto order = this.orderService.getOrderDetailsForAdmin(id);
        return new ResponseMessage<>("주문 상세 조회 성공", String.valueOf(HttpStatus.OK.value()), order);
    }
}
