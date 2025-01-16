package com.team5.nbe341team05.domain.order.dto;

import com.team5.nbe341team05.domain.cartMenu.dto.CartMenuDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    private String email;
    private String address;
    private List<CartMenuDto> menus;
}
