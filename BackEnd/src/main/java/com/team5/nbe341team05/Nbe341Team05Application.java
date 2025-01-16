package com.team5.nbe341team05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling  // 스케줄링 활성화
@EnableJpaAuditing
public class Nbe341Team05Application {

    public static void main(String[] args) {
        SpringApplication.run(Nbe341Team05Application.class, args);
    }
}
