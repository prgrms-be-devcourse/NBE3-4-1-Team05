package com.team5.nbe341team05.common.jpa.entity;

import com.team5.nbe341team05.domain.cart.service.CartService;
import com.team5.nbe341team05.domain.menu.dto.MenuRequestDto;
import com.team5.nbe341team05.domain.menu.service.MenuService;
import com.team5.nbe341team05.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final CartService cartService;
    private final MenuService menuService;
    private final OrderService orderService;

    @Value("${upload.path}")
    String savePath;

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

        for (int i = 1; i <= 8; i++) {
            String fileName = String.format("menu%d.jpg", i );
            Path filePath = Paths.get(savePath + fileName);

            MultipartFile multipartFile = new MockMultipartFile(
                    fileName,
                    fileName,
                    "image/jpeg",
                    Files.readAllBytes(filePath)
            ) {
            };

            MenuRequestDto menuRequestDto = MenuRequestDto.builder()
                    .productName("Menu" + i)
                    .description("Menu description" + i)
                    .price(1000 * i)
                    .stock(10)
                    .build();
            menuService.create(menuRequestDto, multipartFile);
        }
    }
}
