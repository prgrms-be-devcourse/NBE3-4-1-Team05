package com.team5.nbe341team05.domain.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "forward:/index.html";
    }
}
