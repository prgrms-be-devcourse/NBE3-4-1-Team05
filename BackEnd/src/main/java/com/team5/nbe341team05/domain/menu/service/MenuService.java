package com.team5.nbe341team05.domain.menu.service;

import com.team5.nbe341team05.common.exception.ServiceException;
import com.team5.nbe341team05.domain.menu.dto.MenuDto;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.repository.MenuRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Menu updateMenu(long id, MenuDto menuDto) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new ServiceException("404", "해당 상품을 찾을 수 없습니다."));
        menu.update(
                menuDto.getProductName(),
                menuDto.getPrice(),
                menuDto.getStock(),
                menuDto.getImage());
        return menu;
    }

    @Transactional
    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(()->new ServiceException("404","해당 상품을 찾을 수 없습니다."));
        menuRepository.delete(menu);
    }
}