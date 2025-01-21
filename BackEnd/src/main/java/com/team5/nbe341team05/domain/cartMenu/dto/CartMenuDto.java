package com.team5.nbe341team05.domain.cartMenu.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartMenuDto {
    private Long menuId;
    private String menuName;
    private int quantity;
    private Integer price;
}
