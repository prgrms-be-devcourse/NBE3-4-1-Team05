package com.team5.nbe341team05.domain.cartMenu.entity;


import com.team5.nbe341team05.domain.cart.entity.Cart;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class CartMenu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    
    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int quantity;
    private int price;

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public CartMenu(Menu menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }
}
