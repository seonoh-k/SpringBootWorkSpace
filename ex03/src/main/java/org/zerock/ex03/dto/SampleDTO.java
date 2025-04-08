package org.zerock.ex03.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true) // 빌더 기반으로 생성된 객체를 이용하여 새로운 객체를 생성 초기화
public class SampleDTO {
    private Long sno;
    private String first;
    private String last;
    private LocalDateTime regTime;

}
