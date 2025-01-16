package com.team5.nbe341team05.domain.order.controller;

import com.team5.nbe341team05.domain.order.entity.Order;
import com.team5.nbe341team05.domain.order.service.OrderService;
import com.team5.nbe341team05.domain.orderMenu.Dto.OrderMenuDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .andExpect(jsonPath("$.data.order_time").value(order.getCreateDate()))
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
                        get("/order/{email}", "dev@dev.com")
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                ))
                .andDo(print());

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("getOrdersByEmail"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("email 주문 조회 실패 with 없는 email")
    void t5() throws Exception {
        ResultActions resultActions = mvc.perform(
                        get("/order/{email}", "asfasfasf")
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
                        get("/order/{email}{id}", "dev@dev.com", 1)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

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

    @Test
    @DisplayName("email 주문 상세 조회 실패")
    void t7() throws Exception {
        ResultActions resultActions = mvc.perform(
                        get("/order/{email}{id}", "dev@dev.com", 10)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("getOrderDetails"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.msg").value("해당 이메일과 주문 번호로 주문을 찾을 수 없습니다."))
                .andExpect(jsonPath("$.resultCode").value("404"));
    }

    @Test
    @DisplayName("주문 수정 성공")
    void t8() throws Exception {
        ResultActions resultActions = mvc.perform(
                        put("/order/{email}{id}", "dev@dev.com", 1)
                                .content("""
                                        {
                                        ”email”: "dev12@dev.com",
                                        ”address”: "서울(수정)",
                                         "menus": [
                                        {
                                        "menuId": 1
                                        "quantity": 3
                                        },
                                        {
                                        "menuId": 2
                                        "quantity": 4
                                        },
                                        ],
                                        }
                                        """)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        Order order = orderService.findById(1L);

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("updateOrder"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.msg").value("%d번 주문이 성공적으로 수정되었습니다.".formatted(order.getId())))
                .andExpect(jsonPath("$.resultCode").value("200"))
                .andExpect(jsonPath("$.data.id").value(order.getId()))
                .andExpect(jsonPath("$.data.email").value(order.getEmail()))
                .andExpect(jsonPath("$.data.address").value(order.getAddress()))
                .andExpect(jsonPath("$.data.order_time").value(order.getCreateDate()))
                .andExpect(jsonPath("$.data.totalPrice").value(order.getTotalPrice()))
                .andExpect(jsonPath("$.data.deliveryStatus").value(order.isDeliveryStatus()))
                .andExpect(jsonPath("$.data.omlist").value(order.getOrderMenus().stream()
                        .map(orderMenu -> new OrderMenuDto(
                                orderMenu.getMenu().getProductName(),
                                orderMenu.getQuantity())).toList()));
    }

    @Test
    @DisplayName("주문 수정 실패 with 오후 2시 이후 주문")
    void t9() throws Exception {

        Order order = orderService.findById(1L);
        order.updateStatus(true);

        ResultActions resultActions = mvc.perform(
                        put("/order/{email}{id}", "dev@dev.com", 1)
                                .content("""
                                        {
                                        ”email”: "dev12@dev.com",
                                        ”address”: "서울(수정)",
                                         "menus": [
                                        {
                                        "menuId": 1
                                        "quantity": 3
                                        },
                                        {
                                        "menuId": 2
                                        "quantity": 4
                                        },
                                        ],
                                        }
                                        """)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("updateOrder"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("주문수정은 오후 2시이후 주문건만 가능합니다."))
                .andExpect(jsonPath("$.resultCode").value("400"));
    }

    @Test
    @DisplayName("주문 수정 실패 with 존재하지않는 주문번호")
    void t10() throws Exception {
        ResultActions resultActions = mvc.perform(
                        put("/order/{email}{id}", "dev@dev.com", 12)
                                .content("""
                                        {
                                        ”email”: "dev12@dev.com",
                                        ”address”: "서울(수정)",
                                         "menus": [
                                        {
                                        "menuId": 1
                                        "quantity": 3
                                        },
                                        {
                                        "menuId": 2
                                        "quantity": 4
                                        },
                                        ],
                                        }
                                        """)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        Order order = orderService.findById(1L);
        order.updateStatus(true);

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("updateOrder"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.msg").value("해당 주문을 찾을 수 없습니다."))
                .andExpect(jsonPath("$.resultCode").value("404"));
    }

    @Test
    @DisplayName("주문 취소 성공")
    void t11() throws Exception {
        ResultActions resultActions = mvc.perform(
                        put("/order/{email}{id}", "dev@dev.com", 1)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("cancelOrder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("주문이 성공적으로 취소되었습니다."))
                .andExpect(jsonPath("$.resultCode").value("200"));
    }

    @Test
    @DisplayName("주문 취소 실패 with 존재하지않는 주문번호")
    void t12() throws Exception {
        ResultActions resultActions = mvc.perform(
                        put("/order/{email}{id}", "dev@dev.com", 12)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        resultActions.andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("cancelOrder"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.msg").value("해당 주문을 찾을 수 없습니다."))
                .andExpect(jsonPath("$.resultCode").value("404"));
    }

    @Test
    @DisplayName("어드민 주문 취소 성공")
    @WithMockUser(username = "admin", password = "1234", roles = {"ADMIN"})
    void t14() throws Exception {
        ResultActions resultActions = mvc.perform(
                        delete("/admin/order/{id}", 1)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                                .with(SecurityMockMvcRequestPostProcessors.csrf())
                )
                .andDo(print());

        resultActions.andExpect(handler().handlerType(OrderAdminController.class))
                .andExpect(handler().methodName("cancelAdminOrder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("주문이 성공적으로 취소되었습니다."))
                .andExpect(jsonPath("$.resultCode").value("200"));
    }

    @Test
    @DisplayName("어드민 주문 상세 조회 성공")
    @WithMockUser(username = "admin", password = "1234", roles = {"ADMIN"})
    void t15() throws Exception {
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("admin", "1234", Collections.singletonList((new SimpleGrantedAuthority("ADMIN")))));
        ResultActions resultActions = mvc.perform(
                        get("/admin/order/{id}", 1)
                                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        resultActions.andExpect(handler().handlerType(OrderAdminController.class))
                .andExpect(handler().methodName("getOrderDetailByIdForAdmin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("주문 상세 조회 성공"))
                .andExpect(jsonPath("$.resultCode").value("201"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.email").isNotEmpty())
                .andExpect(jsonPath("$.data.address").isNotEmpty())
                .andExpect(jsonPath("$.data.order_time").isNotEmpty())
                .andExpect(jsonPath("$.data.totalPrice").isNotEmpty())
                .andExpect(jsonPath("$.data.deliveryStatus").isNotEmpty())
                .andExpect(jsonPath("$.data.omlist").isNotEmpty());
    }


}