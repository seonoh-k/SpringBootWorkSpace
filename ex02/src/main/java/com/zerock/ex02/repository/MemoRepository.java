package com.zerock.ex02.repository;

import com.zerock.ex02.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface MemoRepository extends JpaRepository<Memo, Long> {
    // insert -> save (엔티티 객체)
    // select -> findById (키 타입)
    // update -> save (엔티티 객체)
    // delete -> deleteByID (키타입), delete (엔티티 객체)

//    @Override
//    <S extends Memo> S save(S entity);
//    @Override
//    List<Memo> findAllById(Iterable<Long> longs);

    // 쿼리 메소드
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    void deleteMemoByMnoLessThan(Long num);

    // @Query 어노테이션
    // @Query(JPQL)
    // JPQL은 클래스의 이름과 변수를 이용해 작성한다.

    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc();

    // 파라미터 받는 방식
    // ?1, ?2
    // :xxx
    // :#{}

    // DML : insert, update, delete
    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.mno  = :mno ")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

//    @Transactional
//    @Modifying
//    @Query("update Memo m set m.memoText = :#{memoText} where m.mno  = :#{mno} ")
//    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

    @Query(value = "select m from Memo m where m.mno > :mno", countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);

    @Query(value = "select m.mno, m.memoText from Memo m where m.mno > :mno", countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);
}
