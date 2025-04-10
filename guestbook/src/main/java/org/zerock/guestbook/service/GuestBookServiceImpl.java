package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestBookDTO;
import org.zerock.guestbook.entity.GuestBook;
import org.zerock.guestbook.repository.GuestBookRepository;

@Service
@Log4j2
@RequiredArgsConstructor // final 상수
public class GuestBookServiceImpl implements GuestBookService{

    private final GuestBookRepository repository;
    @Override
    public Long register(GuestBookDTO guestBookDTO) {
        log.info("dto");
        GuestBook entity = dtoToEntity(guestBookDTO);

        log.info(entity);

        repository.save(entity);

        return entity.getGno();
    }
}
