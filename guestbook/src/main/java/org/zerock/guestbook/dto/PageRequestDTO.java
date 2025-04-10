package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;

    // 페이지 1개당 10개씩 목록으로 보여주는 기본값을 생성자로 세팅
    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort) {
        // page는 항상 0부터 시작해야하므로 page-1이 필요
        return PageRequest.of(page - 1, size, sort);
    }
}
