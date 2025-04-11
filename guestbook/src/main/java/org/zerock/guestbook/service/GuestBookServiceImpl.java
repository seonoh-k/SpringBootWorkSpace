package org.zerock.guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestBookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.GuestBook;
import org.zerock.guestbook.repository.GuestBookRepository;

import java.util.Optional;
import java.util.function.Function;

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

    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        log.info(pageable);

        Page<GuestBook> result = repository.findAll(pageable);
        log.info(result);

        Function<GuestBook, GuestBookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestBookDTO read(Long gno) {
        log.info("read work");

        Optional<GuestBook> entity = repository.findById(gno);

        return entity.isPresent() ? entityToDto(entity.get()) : null;
    }

    @Override
    public void delete(Long gno) {
        log.info("delete work : " + gno);

        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestBookDTO dto) {
        log.info("modify work : " + dto);

        Optional<GuestBook> result = repository.findById(dto.getGno());

        if(result.isPresent()) {
            GuestBook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }
}
