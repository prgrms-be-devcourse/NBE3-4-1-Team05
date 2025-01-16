package com.team5.nbe341team05.domain.menu.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.menu.dto.MenuRequestDto;
import com.team5.nbe341team05.domain.menu.dto.MenuResponseDto;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.service.MenuService;
import com.team5.nbe341team05.domain.menu.type.MenuSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/menus")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class MenuAdminController {

    private final MenuService menuService;

    @GetMapping
    public ResponseMessage<Page<MenuResponseDto>> getAllMenus(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "RECENT") MenuSortType sortType) {
        Page<Menu> menus = menuService.getAllMenus(page, sortType);
        Page<MenuResponseDto> rsMenus = menus.map(MenuResponseDto::new);
        return new ResponseMessage<>(
                "상품이 성공적으로 조회되었습니다.",
                String.valueOf(HttpStatus.OK.value()),
                rsMenus
        );
    }

    @Transactional
    @PostMapping("/")
    public ResponseMessage<Menu> createMenu(@RequestBody MenuRequestDto menuRequestDto) {
        Menu menu = this.menuService.create(menuRequestDto);

        return new ResponseMessage<>("상품이 성공적으로 생성되었습니다.", String.valueOf(HttpStatus.OK.value()), menu);
    }

    @Transactional
    @PostMapping("/{id}")
    public ResponseMessage<Menu> updateMenu(@PathVariable("id") Long id, @RequestBody MenuRequestDto menuRequestDto) {
        Menu menu = this.menuService.updateMenu(id, menuRequestDto);

        return new ResponseMessage<>("상품이 성공적으로 수정되었습니다.", String.valueOf(HttpStatus.OK.value()), menu);

    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseMessage<Void> deleteMenu(@PathVariable("id") long id) {
        menuService.deleteMenu(id);

        return new ResponseMessage<>("상품이 성공적으로 삭제되었습니다.",
                String.valueOf(HttpStatus.OK.value()),
                null);
    }
}
