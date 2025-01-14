package com.team5.nbe341team05.domain.order.service;

import com.team5.nbe341team05.common.exceptions.ServiceException;
import com.team5.nbe341team05.domain.cart.entity.Cart;
import com.team5.nbe341team05.domain.cart.repository.CartRepository;
import com.team5.nbe341team05.domain.cartMenu.dto.CartMenuDto;
import com.team5.nbe341team05.domain.cartMenu.entity.CartMenu;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.repository.MenuRepository;
import com.team5.nbe341team05.domain.order.dto.OrderDto;
import com.team5.nbe341team05.domain.order.dto.OrderResponseDto;
import com.team5.nbe341team05.domain.order.dto.orderUpdateDto.OrderUpdateRequestDto;
import com.team5.nbe341team05.domain.order.entity.Order;
import com.team5.nbe341team05.domain.order.repository.OrderRepository;
import com.team5.nbe341team05.domain.orderMenu.entity.OrderMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
            Menu menu = menuRepository.findById(cartMenuDto.getProductId()).orElseThrow(() -> new ServiceException("404","상품을 찾을 수 없습니다."));

            CartMenu cartMenu = new CartMenu(menu, cartMenuDto.getQuantity());
            cart.addCartMenu(cartMenu);
        }

        LocalDateTime now = LocalDateTime.now();
        boolean orderStatus = now.getHour() < 14;

        int totalPrice = 0;

        Order order = Order.builder()
                .cart(cart)
                .email(orderDto.getEmail())
                .address(orderDto.getAddress())
                .orderTime(LocalDateTime.now())
                .deliveryStatus(orderStatus)
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
    public List<OrderResponseDto> getOrdersByEmail(String email) {
        List<Order> orders = orderRepository.findByEmail(email);
        if (orders.isEmpty()) {
            throw new ServiceException("404", "해당 이메일을 찾을 수 없습니다.");
        }
        return orders.stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderDetails(String email, Long id) {
        Order order = orderRepository.findByEmailAndId(email, id)
                .orElseThrow(() -> new ServiceException("404", "해당 이메일과 주문 번호로 주문을 찾을 수 없습니다."));
        return new OrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto updateOrder(Long id, OrderUpdateRequestDto updateRequestDto) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ServiceException("404", "해당 주문을 찾을 수 없습니다."));

        order.updateOrder(updateRequestDto.getEmail(), updateRequestDto.getAddress());

        orderRepository.save(order);

        return new OrderResponseDto(order);
    }
}
