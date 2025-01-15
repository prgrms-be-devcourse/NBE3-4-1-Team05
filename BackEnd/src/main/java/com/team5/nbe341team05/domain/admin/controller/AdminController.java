package com.team5.nbe341team05.domain.admin.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.admin.service.AdminService;
import com.team5.nbe341team05.domain.menu.dto.MenuDto;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {
    private AdminService adminService;
    private MenuService menuService;

    @Transactional
    @PostMapping("/menus")
    public ResponseMessage<Menu> createMenu(@RequestBody MenuDto menuDto) {
        Menu menu= menuService.createMenu(menuDto);

        return new ResponseMessage<>("상품이 성공적으로 생성되었습니다.",String.valueOf(HttpStatus.OK.value()),menu);
    }

}
