package com.team5.nbe341team05.domain.menu.service;

import com.team5.nbe341team05.domain.menu.dto.MenuDto;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public Menu createMenu(MenuDto menuDto) {
        Menu menu = Menu.builder()
                .productName(menuDto.getProductName())
                .price(menuDto.getPrice())
                .stock(menuDto.getStock())
                .image(menuDto.getImage())
                .build();
        return menuRepository.save(menu);
    }

    public Menu modifyMenu(Menu menu, String productName, int price, int stock, String image) {
        menu = Menu.builder()
                .productName(productName)
                .price(price)
                .stock(stock)
                .image(image)
                .build();
        return menuRepository.save(menu);
    }

    public void deleteMenu(Menu menu) {
        this.menuRepository.delete(menu);
    }
}

