package com.team5.nbe341team05.domain.admin.controller;

import com.team5.nbe341team05.domain.admin.entity.Admin;
import com.team5.nbe341team05.domain.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public Admin joinAdmin(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password) {
        return this.adminService.join(username, password);
    }
}
