package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.BoardImage;
import org.zerock.b01.dto.BoardListAllDTO;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

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

    // 게시물 하나에 3개의 첨부파일을 추가
    @Test
    public void insertWithImages() {
        Board board  = Board.builder()
                .title("Image Test")
                .content("첨부파일테스트")
                .writer("tester")
                .build();

        for (int i = 0; i < 3; i++) {
            board.addImage(UUID.randomUUID().toString(),"file"+i+".jpg");
        }

        boardRepository.save(board);
    }

    @Test
    public void readWithImages() {
        // 반드시 존재하는 bno로 확인
        //Optional<Board> result = boardRepository.findById(1L);
        Optional<Board> result = boardRepository.findByIdWithImages(1L);

        Board board = result.orElseThrow();

        log.info(board);
        log.info(board.getImageSet()); // org. hibernate. LazyInitializationException: failed to lazily initialize a collection of role: org. zerock. b01.domain. Board. imageSet, could not initialize proxy - no Session
        for(BoardImage boardImage : board.getImageSet()) {
            log.info(boardImage);
        }
    }

    @Test
    @Commit
    @Transactional
    public void modifyImages() {
        Optional<Board> result = boardRepository.findByIdWithImages(1L);
        Board board = result.orElseThrow();
        board.clearImages(); // 기존 첨부파일 삭제
        for (int i = 0; i < 2; i++) {
            board.addImage(UUID.randomUUID().toString(), "updatefile"+i+".jpg");
        }
        boardRepository.save(board);
    }

    @Test
    @Commit
    @Transactional
    public void removeAll() {
        Long bno = 1L;
        replyRepository.deleteByBoard_Bno(bno);
        boardRepository.deleteById(bno);
    }

    @Test
    public void insertAll() {
        for (int i = 1; i <= 100; i++) {
            Board board = Board.builder()
                    .title("title..."+i)
                    .content("content..."+i)
                    .writer("user"+i)
                    .build();

            for (int j = 0; j < 3; j++) {
                if (i % 5 == 0) continue;
                board.addImage(UUID.randomUUID().toString(), i+"file"+j+".jpg");
            }

            boardRepository.save(board);
        } // for

    }

    @Transactional
    @Test
    public void testSearchImageReplyCount() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        boardRepository.searchWithAll(null, null, pageable);
    }

    @Transactional
    @Test
    public void testSearchImageReplyCount2() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        //boardRepository.searchWithAll(null, null, pageable);
        Page<BoardListAllDTO> result = boardRepository.searchWithAll(null,null,pageable);

        log.info("---------------------------");
        log.info(result.getTotalElements());

        result.getContent().forEach(boardListAllDTO -> log.info(boardListAllDTO));
    }
}
