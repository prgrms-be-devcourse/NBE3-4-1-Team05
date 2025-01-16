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
import com.team5.nbe341team05.domain.order.dto.OrderUpdateRequestDto;
import com.team5.nbe341team05.domain.order.entity.Order;
import com.team5.nbe341team05.domain.order.repository.OrderRepository;
import com.team5.nbe341team05.domain.orderMenu.entity.OrderMenu;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(OrderDto orderDto) {
        Cart cart = new Cart();
        cartRepository.save(cart);

        for (CartMenuDto cartMenuDto : orderDto.getMenus()) {
            Menu menu = menuRepository.findById(cartMenuDto.getMenuId()).orElseThrow(() -> new ServiceException("404", "상품을 찾을 수 없습니다."));

            if (menu.getStock() < cartMenuDto.getQuantity()) {
                throw new ServiceException("400", "상품 재고가 부족합니다.");
            }
            menu.decreaseStock(cartMenuDto.getQuantity());
            CartMenu cartMenu = new CartMenu(menu, cartMenuDto.getQuantity());
            cart.addCartMenu(cartMenu);
        }

        boolean orderStatus = checkTime();

        Order order = Order.builder()
                .email(orderDto.getEmail())
                .address(orderDto.getAddress())
                .deliveryStatus(orderStatus)
                .totalPrice(0)
                .build();

        for (CartMenu cartMenu : cart.getCartMenus()) {

            OrderMenu orderMenu = new OrderMenu(cartMenu.getMenu(), cartMenu.getQuantity(), cartMenu.getMenu().getPrice());
            order.addOrderMenu(orderMenu);
        }

        order.calculateTotalPrice();
        orderRepository.save(order);
        cart.clear();

        return order;
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrdersByEmail(String email) {
        Optional<List<Order>> orders = orderRepository.findByEmailOrderByIdDesc(email);
        if (orders.isEmpty()) {
            throw new ServiceException("404", "해당 이메일을 찾을 수 없습니다.");
        }
        return orders.get().stream()
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
    public OrderResponseDto updateOrder(String email, Long id, OrderUpdateRequestDto updateRequestDto) {
        if (!checkTime()) {
            throw new ServiceException("400", "주문수정은 오후 2시 이후 주문건만 가능합니다.");
        }

        Order order = orderRepository.findByEmailAndId(email, id)
                .orElseThrow(() -> new ServiceException("404", "해당 주문을 찾을 수 없습니다."));

        for (CartMenuDto cartMenuDto : updateRequestDto.getOmlist()) {
            Menu menu = menuRepository.findById(cartMenuDto.getMenuId()).orElseThrow(() -> new ServiceException("404", "상품을 찾을 수 없습니다."));
        }

        for (OrderMenu orderMenu : order.getOrderMenus()) {
            Menu menu = orderMenu.getMenu();
            int prevQuantity = orderMenu.getQuantity();
            int newQuantity = updateRequestDto.getOmlist().stream()
                    .filter(cartMenuDto -> cartMenuDto.getMenuId().equals(menu.getId()))
                    .map(CartMenuDto::getQuantity)
                    .findFirst()
                    .orElse(prevQuantity);

            int diff = newQuantity - prevQuantity;

            if (diff > 0) {
                if (menu.getStock() < diff) {
                    throw new ServiceException("400", "상품 재고가 부족합니다.");
                }
                menu.decreaseStock(diff);
            } else {
                menu.increaseStock(-diff);
            }

        }

        order.calculateTotalPrice();
        order.updateOrder(updateRequestDto.getEmail(), updateRequestDto.getAddress());
        orderRepository.save(order);

        return new OrderResponseDto(order);
    }

    @Transactional
    public void cancelOrder(String email, Long id) {
        Order order = orderRepository.findByEmailAndId(email, id)
                .orElseThrow(() -> new ServiceException("404", "해당 주문을 찾을 수 없습니다."));

        for (OrderMenu orderMenu : order.getOrderMenus()) {
            Menu menu = orderMenu.getMenu();
            int prevQuantity = orderMenu.getQuantity();
            menu.increaseStock(prevQuantity);
        }

        orderRepository.delete(order);
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ServiceException("404", "상품을 찾을 수 없습니다."));
    }

    @Scheduled(cron = "0 0 14 * * *") // 매일 14:00 실행
    @Transactional
    public void updateDeliveryStatus() {
        List<Order> orderList = orderRepository.findAllByDeliveryStatusFalse();

        for (Order order : orderList) {
            order.updateStatus(true); // 배송 시작 설정
        }

        System.out.println("[자동] 오후 2시 - " + orderList.size() + "개의 주문 상태가 업데이트 되었습니다.");
    }

    public boolean checkTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.getHour() < 14;
    }

    public Optional<Order> findFirst() {
        return orderRepository.findFirstByOrderByIdDesc();
    }

    public Page<OrderResponseDto> getPagedOrders(int page, int size, String sortBy, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return orderRepository.findAll(pageable).map(OrderResponseDto::new);
    }

    @Transactional
    public void cancelAdminOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ServiceException("404", "해당 주문을 찾을 수 없습니다."));
        this.orderRepository.delete(order);
    }

    @Transactional(readOnly = true)
    public OrderResponseDto getOrderDetailsForAdmin(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ServiceException("404", "해당 이메일과 주문 번호로 주문을 찾을 수 없습니다."));
        return new OrderResponseDto(order);
    }
}
