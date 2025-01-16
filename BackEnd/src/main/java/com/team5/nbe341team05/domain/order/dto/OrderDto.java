package com.team5.nbe341team05.domain.order.dto;

import com.team5.nbe341team05.domain.cartMenu.dto.CartMenuDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;
    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;
    private List<CartMenuDto> menus;
}
