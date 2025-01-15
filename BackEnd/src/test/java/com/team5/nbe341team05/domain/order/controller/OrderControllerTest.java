package com.team5.nbe341team05.domain.order.controller;

import com.team5.nbe341team05.domain.order.dto.OrderResponseDto;
import com.team5.nbe341team05.domain.order.entity.Order;
import com.team5.nbe341team05.domain.order.service.OrderService;
import com.team5.nbe341team05.domain.orderMenu.Dto.OrderMenuDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("주문 생성 성공")
    void t1() throws Exception {
        ResultActions resultActions = mvc.perform(
                        post("/order")
                                .content("""
                                        {
                                        ”email”: "dev@dev.com",
                                        ”address”: "서울",
                                         "menus": [
                                        {
                                        "menuId": 1
                                        "quantity": 2
                                        },
                                        {
                                        "menuId": 2
                                        "quantity": 2
                                        },
                                        ],
                                        }
                                        """)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        Order order = orderService.findFirst().get();

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("createOrder"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.msg").value("%d번 주문이 성공적으로 생성되었습니다.".formatted(order.getId())))
                .andExpect(jsonPath("$.resultCode").value("201"))
                .andExpect(jsonPath("$.data.id").value(order.getId()))
                .andExpect(jsonPath("$.data.email").value(order.getEmail()))
                .andExpect(jsonPath("$.data.address").value(order.getAddress()))
                .andExpect(jsonPath("$.data.order_time").value(order.getOrderTime()))
                .andExpect(jsonPath("$.data.totalPrice").value(order.getTotalPrice()))
                .andExpect(jsonPath("$.data.deliveryStatus").value(order.isDeliveryStatus()))
                .andExpect(jsonPath("$.data.omlist").value(order.getOrderMenus().stream()
                        .map(orderMenu -> new OrderMenuDto(
                                orderMenu.getMenu().getProductName(),
                                orderMenu.getQuantity())).toList()));
    }

    @Test
    @DisplayName("주문 실패 with 이메일 빈칸")
    void t2() throws Exception {
        ResultActions resultActions = mvc.perform(
                        post("/order")
                                .content("""
                                        {
                                        ”email”: ""
                                        ”address”: "서울",
                                         "menus": [
                                        {
                                        "menuId": 1
                                        "quantity": 2
                                        },
                                        {
                                        "menuId": 2
                                        "quantity": 2
                                        },
                                        ],
                                        }
                                        """)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("createOrder"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("이메일은 필수 항목입니다."))
                .andExpect(jsonPath("$.resultCode").value("404"));
    }

    @Test
    @DisplayName("주문 실패 with 주소 빈칸")
    void t3() throws Exception {
        ResultActions resultActions = mvc.perform(
                        post("/order")
                                .content("""
                                        {
                                        ”email”: "dev@dev.com"
                                        ”address”: "",
                                         "menus": [
                                        {
                                        "menuId": 1
                                        "quantity": 2
                                        },
                                        {
                                        "menuId": 2
                                        "quantity": 2
                                        },
                                        ],
                                        }
                                        """)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("createOrder"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("주소는 필수 항목입니다."))
                .andExpect(jsonPath("$.resultCode").value("404"));
    }

    @Test
    @DisplayName("email 주문 조회 성공")
    void t4() throws Exception {
        ResultActions resultActions = mvc.perform(
                get("/order/{email}")
                        .content("""
                                {
                                "email": "dev@dev.com"
                                }
                                """)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                        )
                )
                .andDo(print());

        List<OrderResponseDto> orders = orderService.getOrdersByEmail("dev@dev.com");

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("getOrdersByEmail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("email 주문 조회 실패 with 없는 email")
    void t5() throws Exception {
        ResultActions resultActions = mvc.perform(
                get("/order/{email}")
                        .content("""
                                {
                                "email":"asfasfasf"
                                }
                                """)
                        .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                        )
        )
                .andDo(print());

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("getOrdersByEmail"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultCode").value("404"))
                .andExpect(jsonPath("$.msg").value("해당 이메일을 찾을 수 없습니다."));
    }

    @Test
    @DisplayName("email 주문 상세 조회 성공")
    void t6() throws Exception {
        ResultActions resultActions = mvc.perform(
                        get("/order/{email}{id}")
                                .content("""
                                {
                                "email": "dev@dev.com",
                                "id": 1
                                }
                                """)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        OrderResponseDto orderDetails = orderService.getOrderDetails("dev@dev.com", 1L);

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("getOrderDetails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("주문 상세 조회 성공"))
                .andExpect(jsonPath("$.resultCode").value("201"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.email").value("dev@dev.com"))
                .andExpect(jsonPath("$.data.address").isNotEmpty())
                .andExpect(jsonPath("$.data.order_time").isNotEmpty())
                .andExpect(jsonPath("$.data.totalPrice").isNotEmpty())
                .andExpect(jsonPath("$.data.deliveryStatus").isNotEmpty())
                .andExpect(jsonPath("$.data.omlist").isNotEmpty());
    }
}