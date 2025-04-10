package org.zerock.guestbook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 테이블 생성 방지
@Getter
@EntityListeners(value= {AuditingEntityListener.class})
public class BaseEntity {
    @CreatedDate
    @Column(name="regDate", updatable = false)
    private LocalDateTime regDate;

    @CreatedDate
    @LastModifiedDate
    @Column(name="modDate")
    private LocalDateTime modDate;
}
