package com.team5.nbe341team05.domain.orderMenu.entity;


import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderMenu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;
    private int price;

    public void setOrder(Order order) {
        this.order = order;
        order.addOrderMenu(this);
    }

    public OrderMenu(Menu menu, int quantity, int price) {
        this.menu = menu;
        this.quantity = quantity;
        this.price = price;
    }
}
