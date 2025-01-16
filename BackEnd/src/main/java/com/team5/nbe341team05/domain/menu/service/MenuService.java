package com.team5.nbe341team05.domain.menu.service;

import com.team5.nbe341team05.domain.menu.dto.MenuRequestDto;
import com.team5.nbe341team05.domain.menu.entity.Menu;
import com.team5.nbe341team05.domain.menu.repository.MenuRepository;
import com.team5.nbe341team05.domain.menu.type.MenuSortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;
    private static final int PAGE_SIZE = 10; // 한 페이지당 보여줄 메뉴 개수

    @Transactional
    public Page<Menu> getAllMenus(int page, MenuSortType sortType) {
        // 조회순, 최근등록순, 나중등록순, 가격높은순, 가격낮은순
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(sortType.getOrder());

        // 전체 데이터 수를 먼저 확인
        long totalCount = menuRepository.count();
        int totalPages = (int) Math.ceil((double) totalCount / PAGE_SIZE);

        // 페이지 번호 보정
        if (page < 0) {
            page = 0;
        } else if (totalCount > 0 && page >= totalPages) {
            page = totalPages - 1;
        }

        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(sorts));
        return menuRepository.findAll(pageable);
    }

    @Transactional
    public Menu getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("id에 해당하는 메뉴를 찾을 수 없습니다. id : " + id));
        menu.plusView();
        return menu;
    }

    public long count() {
        return menuRepository.count();
    }

    @Transactional
    public Menu create(MenuRequestDto requestDto) {
        Menu menu = Menu.builder()
                .productName(requestDto.getProductName())
                .description(requestDto.getDescription())
                .price(requestDto.getPrice())
                .stock(requestDto.getStock())
                .image(requestDto.getImage())
                .views(0)
                .build();
        Menu saveMenu = menuRepository.save(menu);

        return saveMenu;
    }
}
