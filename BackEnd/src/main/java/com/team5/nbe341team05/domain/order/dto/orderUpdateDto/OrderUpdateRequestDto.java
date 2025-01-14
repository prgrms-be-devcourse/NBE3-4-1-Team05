package com.team5.nbe341team05.domain.order.dto.orderUpdateDto;

import com.team5.nbe341team05.domain.orderMenu.Dto.OrderMenuDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderUpdateRequestDto {
    private String email;
    private String address;
    private List<OrderMenuDto> omlist;

}