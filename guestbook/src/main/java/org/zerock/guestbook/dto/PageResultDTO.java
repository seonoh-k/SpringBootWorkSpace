package org.zerock.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Page<EN> 결과를 List<DTO>로 변환해주는 DTO 클래스
 * - EN: Entity 타입
 * - DTO: 변환할 대상 DTO 타입
 * - 제네릭 타입으로 설계해서 여러 엔티티에 재사용 가능
 */
@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode 포함
public class PageResultDTO<DTO, EN> {

    private List<DTO> dtoList; // Entity -> DTO 변환 결과 리스트

    // 총 페이지 번호
    private int totalPage;
    // 현재 페이지 번호
    private int page;
    // 목록 사이즈
    private int size;
    // 시작 페이지 번호, 끝 페이지 번호
    private int start, end;
    // 이전, 다음
    private boolean prev, next;
    // 페이지 번호 목록
    private List<Integer> pageList;

    /**
     * 생성자
     * @param result: JPA에서 반환된 Page<EN> 객체 (엔티티 리스트)
     * @param fn: Entity를 DTO로 변환해주는 함수형 인터페이스
     *
     * result.stream() 으로 엔티티 리스트를 순회하면서,
     * Function<EN, DTO> fn을 이용해 DTO로 변환한 후 리스트로 수집
     */
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream()
                .map(fn) // 각 엔티티를 DTO로 변환
                .collect(Collectors.toList()); // 리스트로 수집

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {

        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

    }

}
