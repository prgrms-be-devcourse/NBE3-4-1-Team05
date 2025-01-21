package com.team5.nbe341team05.domain.order.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.order.dto.OrderResponseDto;
import com.team5.nbe341team05.domain.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "주문 취소", description = "주문을 취소합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
    public ResponseMessage<OrderResponseDto> cancelAdminOrder(@PathVariable long id) {
        orderService.cancelAdminOrder(id);
        return new ResponseMessage<>(
                "주문이 성공적으로 취소되었습니다.",
                String.valueOf(HttpStatus.OK.value()),
                null);
    }

    @Transactional(readOnly = true)
    @GetMapping
    @Operation(summary = "주문 리스트 조회", description = "주문 리스트를 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
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
    @Operation(summary = "주문 상세 조회", description = "주문 상세 내역을 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
    public ResponseMessage<OrderResponseDto> getOrderDetailByIdForAdmin(@PathVariable long id) {
        OrderResponseDto order = this.orderService.getOrderDetailsForAdmin(id);
        return new ResponseMessage<>("주문 상세 조회 성공", String.valueOf(HttpStatus.OK.value()), order);
    }
}
