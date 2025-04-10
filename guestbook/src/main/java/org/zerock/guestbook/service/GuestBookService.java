package org.zerock.guestbook.service;

import org.zerock.guestbook.dto.GuestBookDTO;
import org.zerock.guestbook.entity.GuestBook;

public interface GuestBookService {
    Long register(GuestBookDTO guestBookDTO);

    // DTO -> Entity 변환
    default GuestBook dtoToEntity(GuestBookDTO dto) {

        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    // Entity -> DTO 변환
    default GuestBookDTO EntityToDto (GuestBook entity) {
        GuestBookDTO dto = GuestBookDTO.builder()
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
