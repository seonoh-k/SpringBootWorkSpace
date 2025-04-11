package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestBookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.GuestBook;

@SpringBootTest
public class GuestBookServiceTests {

    @Autowired
    private GuestBookService guestBookService;

    @Test
    public void insert() {
        GuestBookDTO dto = GuestBookDTO.builder()
                .title("Sample Title")
                .content("Sample Content")
                .writer("Sample User")
                .build();

        System.out.println(guestBookService.register(dto));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResultDTO<GuestBookDTO, GuestBook> resultDTO = guestBookService.getList(pageRequestDTO);

        resultDTO.getDtoList().forEach(System.out::println);
    }

    @Test
    public void testList2() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResultDTO<GuestBookDTO, GuestBook> resultDTO = guestBookService.getList(pageRequestDTO);

        System.out.println("prev : " + resultDTO.isPrev());
        System.out.println("next : " + resultDTO.isNext());
        System.out.println("total : " + resultDTO.getTotalPage());

        resultDTO.getDtoList().forEach(System.out::println);
        System.out.println("=========");
        resultDTO.getPageList().forEach(i -> System.out.println(i));

    }

    @Test
    public void read() {
        GuestBookDTO dto = guestBookService.read(300L);
        System.out.println(dto);
    }
}
