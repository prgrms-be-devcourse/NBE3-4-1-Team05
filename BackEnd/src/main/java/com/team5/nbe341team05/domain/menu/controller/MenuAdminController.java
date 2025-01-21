package com.team5.nbe341team05.domain.menu.controller;

import com.team5.nbe341team05.common.response.ResponseMessage;
import com.team5.nbe341team05.domain.menu.dto.MenuRequestDto;
import com.team5.nbe341team05.domain.menu.dto.MenuResponseDto;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.service.MenuService;
import com.team5.nbe341team05.domain.menu.type.MenuSortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/menus")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class MenuAdminController {

    private final MenuService menuService;

    @GetMapping
    @Operation(summary = "상품 조회", description = "전체 상품을 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
    public ResponseMessage<Page<MenuResponseDto>> getAllMenus(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "RECENT") MenuSortType sortType) {
        Page<Menu> menus = menuService.getAllMenus(page, sortType);
        Page<MenuResponseDto> rsMenus = menus.map(MenuResponseDto::new);
        return new ResponseMessage<>(
                "상품이 성공적으로 조회되었습니다.",
                String.valueOf(HttpStatus.OK.value()),
                rsMenus
        );
    }
    @GetMapping("/{id}")
    @Operation(summary = "특정 상품 조회", description = " 특정 상품을 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
    public ResponseMessage<MenuResponseDto> getMenuById(@PathVariable("id") Long id) {
        Menu menu = menuService.getMenuById(id);
        MenuResponseDto rsMenu = new MenuResponseDto(menu);

        return new ResponseMessage<>(
                String.format("%s번 메뉴가 성공적으로 조회되었습니다.", id),
                String.valueOf(HttpStatus.OK.value()),
                rsMenu
        );
    }

    @PostMapping
    @Operation(summary = "상품 생성", description = "새로운 상품을 생성합니다")
    @ApiResponse(responseCode = "201", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
    public ResponseMessage<MenuResponseDto> createMenu(@RequestPart("menu") MenuRequestDto menuRequestDto
                                                       , @RequestPart("image")MultipartFile image
                                                       ) {
        try {
            Menu menu = this.menuService.create(menuRequestDto, image);
            return new ResponseMessage<>("상품이 성공적으로 생성되었습니다.", String.valueOf(HttpStatus.CREATED.value()),
                    new MenuResponseDto(menu));
        } catch (Exception e) {
            return new ResponseMessage<>("상품 생성 실패: " + e.getMessage(),
                    String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), null);
        }
    }

    @Transactional
    @PostMapping("/{id}")
    @Operation(summary = "상품 수정", description = "상품을 수정합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
    public ResponseMessage<Menu> updateMenu(@PathVariable("id") Long id, @RequestPart(value = "menuRequestDto") MenuRequestDto menuRequestDto, @RequestPart(value = "image") MultipartFile image) throws IOException {
        Menu menu = this.menuService.updateMenu(id, menuRequestDto,image);

        return new ResponseMessage<>("상품이 성공적으로 수정되었습니다.", String.valueOf(HttpStatus.OK.value()), menu);

    }

    @Transactional
    @DeleteMapping("/{id}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "실패")
    public ResponseMessage<Void> deleteMenu(@PathVariable("id") long id) {
        menuService.deleteMenu(id);

        return new ResponseMessage<>("상품이 성공적으로 삭제되었습니다.",
                String.valueOf(HttpStatus.OK.value()),
                null);
    }
}
