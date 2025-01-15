package com.team5.nbe341team05.domain.admin.service;

import com.team5.nbe341team05.domain.admin.entity.Admin;
import com.team5.nbe341team05.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Admin join(String username, String password) {
        Admin admin = Admin.builder()
                .username(username)
                .password(password)
                .build();

        return adminRepository.save(admin);
    }
}
