package com.zerock.ex01.repository;

import com.zerock.ex01.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MemoRepository extends JpaRepository<Memo, Long> {
    // insert -> save (엔티티 객체)
    // select -> findById (키 타입)
    // update -> save (엔티티 객체)
    // delete -> deleteByID (키타입), delete (엔티티 객체)
    @Override
    <S extends Memo> S save(S entity);
    @Override
    List<Memo> findAllById(Iterable<Long> longs);

}
