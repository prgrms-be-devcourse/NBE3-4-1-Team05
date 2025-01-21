package com.team5.nbe341team05.domain.admin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Value("${custom-security.admin.username}")
    private String username;

    @Value("${custom-security.admin.password}")
    private String password;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        if (username.equals(this.username) && password.equals(this.password)) {
            return ResponseEntity.ok("관리자 로그인 성공");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("관리자 로그인 실패");
        }
    }


    @GetMapping("/login")
    public String getLoginPage() {
        return "forward:/index.html";
    }
}
