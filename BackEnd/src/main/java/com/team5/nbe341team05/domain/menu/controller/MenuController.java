package com.team5.nbe341team05.domain.menu.controller;

import com.team5.nbe341team05.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/menus")
@RequiredArgsConstructor
@RestController
public class MenuController {

    private MenuService menuService;



}
