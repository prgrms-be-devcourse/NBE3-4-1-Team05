package com.team5.nbe341team05.domain.menu.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.menu.dto.MenuResponseDto;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.repository.MenuRepository;
import com.team5.nbe341team05.domain.menu.service.MenuService;
import com.team5.nbe341team05.domain.menu.type.MenuSortType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static java.awt.SystemColor.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class MenuControllerTest {

    @Autowired
    private MenuController menuController;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;
    
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws IOException {
        menuRepository.deleteAll();
        //createSampleData();
    }

    private MultipartFile convertStringToMultipartFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null; // 이미지 경로가 없을 경우 null 반환
        }

        try {
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);

            // MockMultipartFile로 변환
            return new MockMultipartFile(
                    "image",            // 필드명
                    file.getName(),     // 원래 파일 이름
                    "image/jpeg",       // MIME 타입 (적절히 변경 가능)
                    inputStream         // 파일 데이터
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null; // 예외 발생 시 null 반환
        }
    }

//    private void createSampleData() throws IOException {
//        // 다양한 가격, 조회수를 가진 테스트 데이터 생성
//        menuService.create(new MenuRequestDto("아메리카노", "기본 커피", 3000, 100, convertStringToMultipartFile("uploads/images/americano.jpg")));
//        menuService.create(new MenuRequestDto("카페라떼", "우유가 들어간 커피", 4000, 100, convertStringToMultipartFile("uploads/images/latte.jpg")));
//        menuService.create(new MenuRequestDto("카푸치노", "거품이 많은 커피", 4500, 100, convertStringToMultipartFile("uploads/images/cappuccino.jpg")));
//        menuService.create(new MenuRequestDto("에스프레소", "진한 커피", 2500, 100, convertStringToMultipartFile("uploads/images/espresso.jpg")));
//        menuService.create(new MenuRequestDto("카라멜마끼아또", "달달한 커피", 5000, 100, convertStringToMultipartFile("uploads/images/caramel.jpg")));
//
//        // 페이징 테스트를 위한 추가 데이터
//        for (int i = 6; i <= 15; i++) {
//            menuService.create(new MenuRequestDto("메뉴" + i,"설명" + i, i * 1000,100,convertStringToMultipartFile("image" + i + ".jpg")));
//        }
//        menuService.getMenuById(menu.getId());
//    }

    @Order(1)
    @Test
    @DisplayName("전체 메뉴 조회 - 최신 등록순")
    void getAllMenus_LastestSort() {
        // when
        ResponseMessage<Page<MenuResponseDto>> response = menuController.getAllMenus(0, MenuSortType.RECENT);

        // then
        assertThat(response.message()).isEqualTo("메뉴가 성공적으로 조회되었습니다.");
        assertThat(response.resultCode()).isEqualTo("200");
        assertThat(response.data().getContent()).hasSize(10);
        assertThat(response.data().getTotalElements()).isEqualTo(15);
    }

    @Order(2)
    @Test
    @DisplayName("전체 메뉴 조회 - 나중 등록순")
    void getAllMenus_OldestSort() {
        // when
        ResponseMessage<Page<MenuResponseDto>> response = menuController.getAllMenus(0, MenuSortType.OLDEST);

        // then
        assertThat(response.message()).isEqualTo("메뉴가 성공적으로 조회되었습니다.");
        assertThat(response.resultCode()).isEqualTo("200");
        assertThat(response.data().getContent()).hasSize(10);
        assertThat(response.data().getTotalElements()).isEqualTo(15);
    }

    @Order(3)
    @Test
    @DisplayName("전체 메뉴 조회 - 가격 높은 순")
    void getAllMenus_PriceDesc() {
        // when
        ResponseMessage<Page<MenuResponseDto>> response = menuController.getAllMenus(0, MenuSortType.PRICE_DESC);

        // then
        Page<MenuResponseDto> menus = response.data();
        assertThat(menus.getContent()).hasSize(10);
        assertThat(menus.getContent().get(0).getPrice())
                .isGreaterThanOrEqualTo(menus.getContent().get(1).getPrice());
    }

    @Order(4)
    @Test
    @DisplayName("전체 메뉴 조회 - 가격 낮은 순")
    void getAllMenus_PriceAsc() {
        // when
        ResponseMessage<Page<MenuResponseDto>> response = menuController.getAllMenus(0, MenuSortType.PRICE_ASC);

        // then
        Page<MenuResponseDto> menus = response.data();
        assertThat(menus.getContent()).hasSize(10);
        assertThat(menus.getContent().get(0).getPrice())
                .isLessThanOrEqualTo(menus.getContent().get(1).getPrice());
    }

    @Order(5)
    @Test
    @DisplayName("전체 메뉴 조회 - 조회수 높은 순")
    void getAllMenus_ViewsDesc() {
        // when
        ResponseMessage<Page<MenuResponseDto>> response = menuController.getAllMenus(0, MenuSortType.VIEWS_DESC);

        // then
        Page<MenuResponseDto> menus = response.data();
        assertThat(menus.getContent()).hasSize(10);
        assertThat(menus.getContent().get(0).getViews())
                .isGreaterThanOrEqualTo(menus.getContent().get(1).getViews());
    }

    @Order(6)
    @Test
    @DisplayName("정렬 조건별 첫 번째 데이터 검증")
    void sortingValidation() {
        // 최신 등록순
        ResponseMessage<Page<MenuResponseDto>> recent = menuController.getAllMenus(0, MenuSortType.RECENT);
        assertThat(recent.data().getContent().get(0).getProductName()).isEqualTo("메뉴15");

        // 나중 등록순
        ResponseMessage<Page<MenuResponseDto>> oldest = menuController.getAllMenus(0, MenuSortType.OLDEST);
        assertThat(oldest.data().getContent().get(0).getProductName()).isEqualTo("아메리카노");

        // 가격 높은순
        ResponseMessage<Page<MenuResponseDto>> priceDesc = menuController.getAllMenus(0, MenuSortType.PRICE_DESC);
        assertThat(priceDesc.data().getContent().get(0).getPrice()).isEqualTo(15000);

        // 가격 낮은순
        ResponseMessage<Page<MenuResponseDto>> priceAsc = menuController.getAllMenus(0, MenuSortType.PRICE_ASC);
        assertThat(priceAsc.data().getContent().get(0).getPrice()).isEqualTo(2500);

        // 조회수순
        ResponseMessage<Page<MenuResponseDto>> viewsDesc = menuController.getAllMenus(0, MenuSortType.VIEWS_DESC);
        assertThat(viewsDesc.data().getContent().get(0).getViews()).isEqualTo(1);
    }

    @Order(7)
    @Test
    @DisplayName("메뉴 단건 조회 성공")
    void getMenuById_Success() {
        // given
        List<Menu> menus = menuService.getAllMenus(0, MenuSortType.RECENT).getContent();
        Menu menu1 = menus.get(0);


        // when
        ResponseMessage<MenuResponseDto> response = menuController.getMenuById(menu1.getId());

        // then
        assertThat(response.message()).contains("번 메뉴가 성공적으로 조회되었습니다.");
        assertThat(response.data().getImage()).isEqualTo(menu1.getImage());
        assertThat(response.resultCode()).isEqualTo("200");
        assertThat(response.data().getId()).isEqualTo(menu1.getId());
    }

    @Order(8)
    @Test
    @DisplayName("존재하지 않는 메뉴 ID로 조회시 예외 발생")
    void getMenuById_NotFound() {
        // given
        Long nonExistentId = -1L;

        // when & then
        assertThatThrownBy(() -> menuController.getMenuById(nonExistentId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("id에 해당하는 메뉴를 찾을 수 없습니다. id : " + nonExistentId);
    }

    @Order(9)
    @Test
    @DisplayName("페이징 처리 검증")
    void pagination() {
        // when
        ResponseMessage<Page<MenuResponseDto>> firstPage = menuController.getAllMenus(0, MenuSortType.RECENT);
        ResponseMessage<Page<MenuResponseDto>> secondPage = menuController.getAllMenus(1, MenuSortType.RECENT);

        // then
        assertThat(firstPage.data().getNumber()).isEqualTo(0);
        assertThat(firstPage.data().getSize()).isEqualTo(10);
        assertThat(firstPage.data().hasNext()).isTrue();

        assertThat(secondPage.data().getNumber()).isEqualTo(1);
        assertThat(secondPage.data().getContent()).hasSizeLessThan(10); // 남은 데이터만큼만
        assertThat(secondPage.data().hasNext()).isFalse();
    }

    @Order(10)
    @Test
    @DisplayName("전체 메뉴의 총 개수가 정확한지 검증")
    void count() {
        // when
        long totalCount = menuService.count();

        // then
        assertThat(totalCount).isEqualTo(15); // setUp에서 생성한 데이터 수
    }

    @Order(11)
    @Test
    @DisplayName("페이지 범위 요청 처리 검증")
    void pageRangeRequest() {
        // 음수 페이지 요청
        ResponseMessage<Page<MenuResponseDto>> negativeResponse =
                menuController.getAllMenus(-1, MenuSortType.RECENT);
        assertThat(negativeResponse.data().getNumber()).isEqualTo(0);
        assertThat(negativeResponse.data().getContent()).hasSize(10);

        // 범위 초과 페이지 요청
        ResponseMessage<Page<MenuResponseDto>> overResponse =
                menuController.getAllMenus(99, MenuSortType.RECENT);
        assertThat(overResponse.data().getNumber()).isEqualTo(1);  // 마지막 페이지(2페이지)
        assertThat(overResponse.data().getContent()).hasSize(5);   // 남은 5개 데이터
        assertThat(overResponse.data().getTotalElements()).isEqualTo(15);
    }
    @Order(12)
    @Test
    @DisplayName("이미지 저장")
    void testSaveImage() throws IOException {
        FileInputStream stream = new FileInputStream(new File("./sample.jpg"));
        MultipartFile file =  new MockMultipartFile("sample.jpg","sample.jpg","jpg",stream.readAllBytes());
        String filePath = menuService.saveImage(file);
        System.out.println(filePath);
    }

    @Order(13)
    @Test
    @DisplayName("메뉴 생성 테스트")
    @WithMockUser(username = "admin", password = "1234", roles = {"ADMIN"})
    void createMenu() throws Exception {
        MockMultipartFile testFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "Test image".getBytes()
        );

        MockMultipartFile testJson = new MockMultipartFile(
                "menu",
                "",
                "application/json",
                """
                 {
                   "productName": "에스프레소",
                   "description": "에스프레소 커피",
                   "price" : 3000,
                   "stock" : 100
                 }
                 """.getBytes());

        mockMvc.perform(
                MockMvcRequestBuilders.multipart("/admin/menus")
                .file(testFile)
                        .file(testJson)
                .contentType(new MediaType(MediaType.MULTIPART_FORM_DATA)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("상품이 성공적으로 생성되었습니다."))
                .andExpect(jsonPath("$.data.productName").value("에스프레소"))
                .andExpect(jsonPath("$.data.description").value("에스프레소 커피"))
                .andExpect(jsonPath("$.data.price").value(3000))
                .andExpect(jsonPath("$.data.stock").value(100))
                .andDo(print());
    }
}
