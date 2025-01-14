package com.team5.nbe341team05.domain.admin.service;

import com.team5.nbe341team05.domain.admin.repository.AdminRepository;
import com.team5.nbe341team05.domain.menu.repository.MenuRepository;
import com.team5.nbe341team05.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final AdminRepository adminRepository;

}
