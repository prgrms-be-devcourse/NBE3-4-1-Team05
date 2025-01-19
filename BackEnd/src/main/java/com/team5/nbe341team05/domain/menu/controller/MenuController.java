package com.team5.nbe341team05.domain.menu.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.menu.dto.MenuResponseDto;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.service.MenuService;
import com.team5.nbe341team05.domain.menu.type.MenuSortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menus")
@Tag(name = "Menu", description = "Menu API")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    @Operation(summary = "전체 메뉴 조회")
    public ResponseMessage<Page<MenuResponseDto>> getAllMenus(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "RECENT")MenuSortType sortType) {
        Page<Menu> menus = menuService.getAllMenus(page, sortType);
        Page<MenuResponseDto> rsMenus = menus.map(MenuResponseDto::new);

        return new ResponseMessage<>(
                "메뉴가 성공적으로 조회되었습니다.",
                String.valueOf(HttpStatus.OK.value()),
                rsMenus
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "특정 메뉴 조회")
    public ResponseMessage<MenuResponseDto> getMenuById(@PathVariable("id") Long id) {
        Menu menu = menuService.getMenuById(id);
        MenuResponseDto rsMenu = new MenuResponseDto(menu);

        return new ResponseMessage<>(
                String.format("%s번 메뉴가 성공적으로 조회되었습니다.", id),
                String.valueOf(HttpStatus.OK.value()),
                rsMenu
        );
    }
}