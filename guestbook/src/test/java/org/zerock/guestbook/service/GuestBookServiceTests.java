package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestBookDTO;

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
}
