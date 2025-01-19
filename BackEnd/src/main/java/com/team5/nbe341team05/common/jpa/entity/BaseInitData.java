package com.team5.nbe341team05.common.jpa.entity;

import com.team5.nbe341team05.domain.cart.service.CartService;
import com.team5.nbe341team05.domain.menu.dto.MenuRequestDto;
import com.team5.nbe341team05.domain.menu.service.MenuService;
import com.team5.nbe341team05.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final CartService cartService;
    private final MenuService menuService;
    private final OrderService orderService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.makeSampleMenus();
        };
    }

    @Transactional
    public void makeSampleMenus() throws IOException {
        if (menuService.count() > 0) return;

        for (int i = 1; i <= 20; i++) {
            MenuRequestDto menuRequestDto = MenuRequestDto.builder()
                    .productName("Menu" + i)
                    .description("Menu description" + i)
                    .price(1000 * i)
                    .stock(10)
                    .build();
            menuService.create(menuRequestDto, null);
        }

//        String[] menuTypes = {"커피", "주스", "스무디", "티", "에이드"};
//        String[] descriptionsTemplate = {
//                "깊고 진한 맛의 ",
//                "상큼하고 달콤한 ",
//                "시원하고 청량한 ",
//                "부드럽고 향긋한 ",
//                "달콤쌉싸름한 "
//        };
//
//        for (int i = 1; i <= 200; i++) {
//            // 메뉴 종류를 랜덤하게 선택
//            String menuType = menuTypes[i % 5];
//            String description = descriptionsTemplate[i % 5];
//
//            menuService.create(
//                    menuType + " " + i + "호", // 예: "커피 1호", "주스 2호" 등
//                    description + menuType + "입니다.", // 예: "깊고 진한 맛의 커피입니다."
//                    ((i % 3) + 3) * 1000, // 3000~5000원 범위의 가격
//                    100 + (i % 50), // 100~149개 범위의 재고
//                    "menu" + i + ".jpg" // menu1.jpg, menu2.jpg 등
//            );
//        }
    }
}