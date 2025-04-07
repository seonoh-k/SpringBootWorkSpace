package com.zerock.ex02.repository;

import com.zerock.ex02.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i ->{
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        Long mno = 300L;

        Optional<Memo> result = memoRepository.findById(mno);

        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Test
    public void testUpdate(){
        Long mno = 100L;

        Memo memo = Memo.builder()
                .memoText("Update Text")
                .build();
        memoRepository.save(memo);
    }

    @Test
    public void testDelete() {
        Long mno = 301L;

        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {
        // Pageable : 페이징처리 인터페이스
        // PageRequest : 페이징처리 클래스

        Pageable pageable = PageRequest.of(0,10);

        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);

        System.out.println("========");


        System.out.println("Total Pages : " + result.getTotalPages()); // 총 페이지 개수
        System.out.println("Total Count : " + result.getTotalElements()); // 총 글의 개수
        System.out.println("Page Number : " + result.getNumber()); // 현재 페이지 번호 0부터 시작
        System.out.println("Page Size : " + result.getSize()); // 총 글의 개수
        System.out.println("Has next Page : " + result.hasNext()); // 다음 블록 이동 유무
        System.out.println("First Page : " + result.isFirst()); // 시작 블록 이동 유무

        for(Memo memo : result.getContent()) {
            System.out.println(memo);
        }

//        result.get().forEach(memo -> { System.out.println(memo);});

//        result.stream().map(람다함수).collect(Collectors.toList());
    }

    @Test
    public void testSort() {

        Sort sort = Sort.by("mno").descending();
        
        // 두가지 이상의 정렬 조건
//        Sort sort1 = Sort.by("memoText").ascending();
//
//        Sort sortAll = sort.and(sort1);
//        
//        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Pageable pageable = PageRequest.of(0, 10, sort);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> { System.out.println(memo);});
    }

    @Test
    public void testQueryMethod() {
        List<Memo> memoList = memoRepository.findByMnoBetweenOrderByMnoDesc(280L, 290L);

        memoList.forEach(System.out::println);

    }

    @Test
    public void testPageMethod() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(201L, 300L, pageable);

        result.get().forEach(System.out::println);
    }

    // delete 시 강제
    @Transactional
    @Commit
    @Test
    public void testDeleteMethod() {
        memoRepository.deleteMemoByMnoLessThan(202L);
    }
}
