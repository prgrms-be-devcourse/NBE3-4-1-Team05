package com.team5.nbe341team05.common.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseTime extends BaseEntity {
    @CreatedDate
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime createDate;

    @LastModifiedDate  // @LastModifiedDate는 엔티티가 처음 생성될 때도 값이 설정됨.
    @Column(insertable = false) // 처음 생성 시에는 null이 들어가고 수정 시에만 현재 시간이 들어가도록 설정
    @Setter(AccessLevel.PRIVATE)
    private LocalDateTime modifyDate;
}
