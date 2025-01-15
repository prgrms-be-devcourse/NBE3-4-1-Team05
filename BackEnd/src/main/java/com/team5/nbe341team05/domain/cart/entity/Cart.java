package com.team5.nbe341team05.domain.cart.entity;

import com.team5.nbe341team05.domain.cartMenu.entity.CartMenu;
import com.team5.nbe341team05.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToMany(mappedBy = "cart")
    private List<CartMenu> cartMenus = new ArrayList<>();

//    @OneToOne(mappedBy = "order_id")
//    private Order order;

    public void addCartProduct(CartMenu cartmenu) {
        cartMenus.add(cartmenu);
        cartmenu.setCart(this);
    }

    public void clear() {
        for (CartMenu cartMenu : cartMenus) {
            cartMenu.setCart(null);
        }
        this.cartMenus.clear();
    }
}
