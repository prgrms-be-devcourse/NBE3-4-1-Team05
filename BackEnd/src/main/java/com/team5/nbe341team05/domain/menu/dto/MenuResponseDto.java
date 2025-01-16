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
public class MenuResponseDto {
    private Long id;
    private String productName;
    private String description;
    private int price;
    private int stock;
    private String image;
    private long views;

    public MenuResponseDto(Menu menu) {
        this.id = menu.getId();
        this.productName = menu.getProductName();
        this.description = menu.getDescription();
        this.price = menu.getPrice();
        this.stock = menu.getStock();
        this.image = menu.getImage();
        this.views = menu.getViews();
    }
}
