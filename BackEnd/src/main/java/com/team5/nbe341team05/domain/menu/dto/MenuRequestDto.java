package com.team5.nbe341team05.domain.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestDto {
    private String productName;
    private String description;
    private int price;
    private int stock;
}
