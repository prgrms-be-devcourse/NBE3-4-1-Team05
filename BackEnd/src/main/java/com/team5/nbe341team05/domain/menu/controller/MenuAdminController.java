package com.team5.nbe341team05.domain.menu.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.menu.dto.MenuDto;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/menus")
@PreAuthorize("hasRole('ADMIN')")
public class MenuAdminController {

    private MenuService menuService;

    @Transactional
    @PostMapping("/")
    public ResponseMessage<Menu> createMenu(@RequestBody MenuDto menuDto) {
        Menu menu = this.menuService.createMenu(menuDto);

        return new ResponseMessage<>("상품이 성공적으로 생성되었습니다.", String.valueOf(HttpStatus.OK.value()), menu);
    }

    @Transactional
    @PostMapping("/{id}")
    public ResponseMessage<Menu> updateMenu(@PathVariable("id") Long id, @RequestBody MenuDto menuDto) {
        Menu menu = this.menuService.updateMenu(id, menuDto);

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
