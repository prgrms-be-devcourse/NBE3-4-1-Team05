package com.team5.nbe341team05.domain.menu.entity;

import com.team5.nbe341team05.domain.cartMenu.entity.CartMenu;
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
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;            // 기본 키

    @OneToMany(mappedBy = "menu")
    private List<CartMenu> cartMenus = new ArrayList<>();
    
    @OneToMany(mappedBy = "menu")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @NonNull
    private String productName; // 제품명

    @Column(length = 300)
    private String description;

    private int price;          // 가격

    private int stock;          // 재고 수량

    private String image;       // 이미지 URL 또는 경로
}

