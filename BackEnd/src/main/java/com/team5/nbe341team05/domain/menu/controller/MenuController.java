package com.team5.nbe341team05.domain.menu.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.menu.dto.MenuResponseDto;
import com.team5.nbe341team05.domain.menu.service.MenuService;
import com.team5.nbe341team05.domain.menu.type.MenuSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    public ResponseMessage<Page<MenuResponseDto>> getAllMenus(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "RECENT")MenuSortType sortType) {
        Page<MenuResponseDto> menus = menuService.getAllMenus(page, sortType);
        return new ResponseMessage<>(
                "메뉴가 성공적으로 조회되었습니다.",
                String.valueOf(HttpStatus.OK.value()),
                menus
        );
    }

    @GetMapping("/{id}")
    public ResponseMessage<MenuResponseDto> getMenuById(@PathVariable("id") Long id) {
        MenuResponseDto menu = menuService.getMenuById(id);
        return new ResponseMessage<>(
                String.format("%s번 메뉴가 성공적으로 조회되었습니다.", id),
                String.valueOf(HttpStatus.OK.value()),
                menu
        );
    }
}