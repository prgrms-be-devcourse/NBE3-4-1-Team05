package com.team5.nbe341team05.common.jpa.entity;

import com.team5.nbe341team05.domain.admin.service.AdminService;
import com.team5.nbe341team05.domain.menu.service.MenuService;
import com.team5.nbe341team05.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final MenuService menuService;
    private final AdminService adminService;
    private final OrderService orderService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
//            // 상품 생성. gpt코드여서 참고해주세요
//            var menu1 = menuService.createMenu("아메리카노", 4000, 50, "americano.jpg");
//            var menu2 = menuService.createMenu("카페라떼", 4500, 30, "latte.jpg");
//            var menu3 = menuService.createMenu("모카", 5000, 20, "mocha.jpg");
//
//            // 장바구니 생성
//            var cart1 = cartService.createCart("user1@example.com");
//            var cart2 = cartService.createCart("user2@example.com");
//
//            // 장바구니-상품 추가
//            cartService.addItemToCart(cart1.getId(), menu1.getId(), 2);
//            cartService.addItemToCart(cart1.getId(), menu2.getId(), 1);
//            cartService.addItemToCart(cart2.getId(), menu3.getId(), 3);
//
//            // 주문 생성
//            var order1 = orderService.createOrder("user1@example.com", "2025-01-14 10:00:00", "서울시 강남구", true, 12000);
//            var order2 = orderService.createOrder("user2@example.com", "2025-01-14 11:00:00", "서울시 서초구", false, 15000);
//
//            // 주문-상품 추가
//            orderService.addOrderItem(order1.getId(), menu1.getId(), 2);
//            orderService.addOrderItem(order1.getId(), menu2.getId(), 1);
//            orderService.addOrderItem(order2.getId(), menu3.getId(), 3);
        };
    }
}
