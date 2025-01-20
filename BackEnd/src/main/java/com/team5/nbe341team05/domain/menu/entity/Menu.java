package com.team5.nbe341team05.domain.menu.entity;

import com.team5.nbe341team05.common.jpa.entity.BaseTime;
import com.team5.nbe341team05.domain.cartMenu.entity.CartMenu;
import com.team5.nbe341team05.domain.menu.dto.MenuRequestDto;
import com.team5.nbe341team05.domain.orderMenu.entity.OrderMenu;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;            // 기본 키

    @OneToMany(mappedBy = "menu", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartMenu> cartMenus = new ArrayList<>();

    @OneToMany(mappedBy = "menu", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @NonNull
    private String productName; // 제품명

    @Column(length = 300)
    private String description;

    private int price;          // 가격

    private int stock;          // 재고 수량

    private String image;       // 이미지 URL 또는 경로

    public void decreaseStock(int diff) {
        this.stock -= diff;
    }

    public void increaseStock(int diff) {
        this.stock += diff;
    }

    private long views;              // 메뉴 조회수

    public void plusView(){
        this.views++;
    }

    public void update(String productName, int price, int stock, String image) {
        this.productName = productName;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    public Menu(MenuRequestDto menuRequestDto, String imagePath) {
        this.productName = menuRequestDto.getProductName();
        this.description = menuRequestDto.getDescription();
        this.price = menuRequestDto.getPrice();
        this.stock = menuRequestDto.getStock();
        this.image = imagePath; // 이미지 경로 설정
    }
    @Builder
    public Menu(String productName, String description, int price, int stock, String image) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }
}


