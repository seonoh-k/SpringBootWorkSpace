package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.GuestBook;
import org.zerock.guestbook.entity.QGuestBook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GusetBookRepositoryTests {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void insertDummies() {
        IntStream.range(1, 300).forEach(i -> {
            GuestBook guestBook = GuestBook.builder()
                    .title("title : " + i)
                    .content("content : " + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestBookRepository.save(guestBook));
        });
    }

    @Test
    public void updateTest() {
        Optional<GuestBook> result = guestBookRepository.findById(299L);

        if(result.isPresent()) {
            GuestBook guestBook = result.get();

            guestBook.changeTitle("ChangeTitle");
            guestBook.changeContent("ChangeContent");

            guestBookRepository.save(guestBook);
        }
    }

    @Test
    public void testQuery1() { // Querydsl
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression expression = qGuestBook.title.contains(keyword);

        builder.and(expression);

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        result.stream().forEach(System.out::println);
    }

//    Hibernate:
//    select gb1_0.gno, gb1_0.content, gb1_0.mod_date, gb1_0.reg_date, gb1_0.title, gb1_0.writer
//        from guest_book gb1_0 where gb1_0.title like ? escape '!' order by gb1_0.gno desc limit ?, ?
//    Hibernate:
//    select count(gb1_0.gno) from guest_book gb1_0 where gb1_0.title like ? escape '!'


    @Test
    public void testQuery2() { // Querydsl
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestBook qGuestBook = QGuestBook.guestBook;
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        BooleanExpression exContent = qGuestBook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);

        builder.and(qGuestBook.gno.gt(0L));

        Page<GuestBook> result = guestBookRepository.findAll(builder, pageable);

        result.stream().forEach(System.out::println);
    }

//    Hibernate:
//    select gb1_0.gno, gb1_0.content, gb1_0.mod_date, gb1_0.reg_date, gb1_0.title, gb1_0.writer
//        from guest_book gb1_0 where (gb1_0.title like ? escape '!' or gb1_0.content like ? escape '!') and gb1_0.gno>?
//        order by gb1_0.gno desc limit ?, ?
//    Hibernate:
//    select count(gb1_0.gno) from guest_book gb1_0 where ( gb1_0.title like ? escape '!' or gb1_0.content like ? escape '!' ) and gb1_0.gno>?
}
