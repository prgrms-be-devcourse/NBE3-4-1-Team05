package com.team5.nbe341team05.domain.menu.dto;

import com.team5.nbe341team05.domain.menu.entity.Menu;
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
    private String image;

    public MenuRequestDto(Menu menu) {
        this.productName = menu.getProductName();
        this.description = menu.getDescription();
        this.price = menu.getPrice();
        this.stock = menu.getStock();
        this.image = menu.getImage();
    }
}
