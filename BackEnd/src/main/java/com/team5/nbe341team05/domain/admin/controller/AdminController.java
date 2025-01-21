package com.team5.nbe341team05.domain.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Value("${custom-security.admin.username}")
    private String username;

    @Value("${custom-security.admin.password}")
    private String password;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        if (username.equals(this.username) && password.equals(this.password)) {
            System.out.println("로그인 성공: " + username); // 디버깅 로그
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    username, password
            );
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("관리자 로그인 성공");
        }else {
            System.out.println("로그인 실패 - 입력값: " + username + ", " + password); // 디버깅 로그
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("관리자 로그인 실패");
        }
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "forward:/index.html";
    }
}
