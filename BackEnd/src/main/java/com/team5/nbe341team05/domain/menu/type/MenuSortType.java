package com.team5.nbe341team05.domain.menu.type;

import org.springframework.data.domain.Sort;

public enum MenuSortType {
    VIEWS_DESC("views", Sort.Direction.DESC),          // 조회순
    RECENT("createDate", Sort.Direction.DESC),         // 최근등록순
    OLDEST("createDate", Sort.Direction.ASC),          // 나중등록순
    PRICE_DESC("price", Sort.Direction.DESC),          // 가격높은순
    PRICE_ASC("price", Sort.Direction.ASC);            // 가격낮은순

    private final String field;
    private final Sort.Direction direction;

    MenuSortType(String field, Sort.Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    public Sort.Order getOrder() {
        return new Sort.Order(direction, field);
    }
}
