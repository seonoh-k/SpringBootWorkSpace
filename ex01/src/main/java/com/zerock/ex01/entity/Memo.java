package com.zerock.ex01.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name="tbl_memo") // 테이블 설정
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Memo {

    @Id // 기본키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;


}
