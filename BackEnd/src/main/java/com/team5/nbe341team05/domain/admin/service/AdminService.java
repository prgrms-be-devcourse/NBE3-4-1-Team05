package com.team5.nbe341team05.domain.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    /*@Value("${custom-security.admin.username}")
    private String adminUsername;

    @Value("${custom-security.admin.password}")
    private String adminPassword;

    @Value("${custom-security.admin.role}")
    private String adminRole;

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("관리자가 아닙니다."));
    }

    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .roles(adminRole)
                .build();

        return new InMemoryUserDetailsManager(admin);
    }*/
}
