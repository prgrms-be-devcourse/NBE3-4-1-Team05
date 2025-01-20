package com.team5.nbe341team05.domain.orderMenu.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderMenuDto {
    private Long menuId;
    private String name;
    private int quantity;
}
