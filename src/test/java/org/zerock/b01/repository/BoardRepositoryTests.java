package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    //439
    @Test
    public void insertOne() {
        Board board = Board.builder()
                .title("title...1")
                .content("content...1")
                .writer("user1")
                .build();

        Board result = boardRepository.save(board);
        log.info("BNO: " + result.getBno());
    }

    @Test
    public void insert() {
        IntStream.rangeClosed(2,100).forEach(i -> {
            Board board = Board.builder()
                    .title("title..."+i)
                    .content("content..."+i)
                    .writer("user"+i)
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO: " + result.getBno());
        });

    }
    //SELECT * FROM board ORDER BY bno desc;

    @Test
    public void select() {
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        log.info(board);
    }

    // #### update 기능 테스트 p441
    @Test
    public void update() {
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();
        board.change("title changed 100", "content changed 100");
        boardRepository.save(board);
    }

    // delete p442
    @Test
    public void delete() {
        Long bno = 1L;
        boardRepository.deleteById(bno);
    }

    // Pageable과 Page<E> 타입 p443
    @Test
    public void paging() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        // pageNumber, pageSize, sort
        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count=" + result.getTotalElements()); // 402 total amount of elements.
        log.info("total pages=" + result.getTotalPages()); // 42        21      number of total page
        log.info("page number=" + result.getNumber()); // 0     1
        log.info("page size = " + result.getSize()); // 10      20
        List<Board> todoList = result.getContent();
        todoList.forEach(board -> log.info(board));
    }

    @Test
    public void search1() {
        // 1 = 2번째 page
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());
        boardRepository.search1(pageable);
    }

    @Test
    public void searchAll() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.seachAll(types, keyword, pageable);
    }

    @Test
    public void searchAll2() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> result = boardRepository.seachAll(types, keyword, pageable);
        //log.info("getTotalElements={}", result.getTotalElements()); //20
        log.info("getTotalPages={}", result.getTotalPages());
        log.info("getSize={}", result.getSize());
        log.info("getNumber={}", result.getNumber());
        log.info("hasPrevious={}", result.hasPrevious());
        log.info("hasNext={}", result.hasNext());
        result.getContent().forEach(log::info);
    }

    @Test
    public void testSearchReplyCount() {

        String[] types = {"t","c","w"};

        String keyword = "1";

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types, keyword, pageable );

        //total pages
        log.info(result.getTotalPages());
        //pag size
        log.info(result.getSize());
        //pageNumber
        log.info(result.getNumber());
        //prev next
        log.info(result.hasPrevious() +": " + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

}
