package com.team5.nbe341team05.domain.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class Admin {

    @Id
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;
}
