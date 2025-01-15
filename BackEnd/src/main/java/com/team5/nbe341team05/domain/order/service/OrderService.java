package com.team5.nbe341team05.domain.order.service;

import com.team5.nbe341team05.common.exception.ServiceException;
import com.team5.nbe341team05.domain.cart.entity.Cart;
import com.team5.nbe341team05.domain.cart.repository.CartRepository;
import com.team5.nbe341team05.domain.cartMenu.dto.CartMenuDto;
import com.team5.nbe341team05.domain.cartMenu.entity.CartMenu;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.repository.MenuRepository;
import com.team5.nbe341team05.domain.order.dto.OrderDto;
import com.team5.nbe341team05.domain.order.dto.OrderResponseDto;
import com.team5.nbe341team05.domain.order.entity.Order;
import com.team5.nbe341team05.domain.order.repository.OrderRepository;
import com.team5.nbe341team05.domain.orderMenu.entity.OrderMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(OrderDto orderDto) {
        Cart cart = new Cart();
        cartRepository.save(cart);

        for (CartMenuDto cartMenuDto : orderDto.getProducts()) {
            Menu menu = menuRepository.findById(cartMenuDto.getProductId()).orElseThrow(() -> new ServiceException("400","상품을 찾을 수 없습니다."));

            CartMenu cartMenu = new CartMenu(menu, cartMenuDto.getQuantity());
            cart.addCartProduct(cartMenu);
        }

        LocalDateTime now = LocalDateTime.now();
        boolean orderStatus = now.getHour() < 14;

        int totalPrice = 0;

        Order order = Order.builder()
                .email(orderDto.getEmail())
                .address(orderDto.getAddress())
                .orderTime(LocalDateTime.now())
                .deliveryStatus(orderStatus)
                .totalPrice(0)
                .build();

        for (CartMenu cartMenu : cart.getCartMenus()) {
            int price = cartMenu.getMenu().getPrice();
            int tPrice = price * cartMenu.getQuantity();

            OrderMenu orderMenu = new OrderMenu(cartMenu.getMenu(), cartMenu.getQuantity(), cartMenu.getMenu().getPrice());
            order.addOrderMenu(orderMenu);
            totalPrice += tPrice;
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        cart.clear();

        return order;
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getPagedOrders(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(OrderResponseDto::new);
    }

    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ServiceException("404", "해당 주문을 찾을 수 없습니다."));
        this.orderRepository.delete(order);
    }

    @Transactional
    public void getOrder(Long id) {}
}
