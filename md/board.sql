-- board
SELECT * FROM board;
-- board
SELECT * FROM board ORDER BY bno DESC;

-- Querydsl로 검색 조건과 목록 처리 454

SELECT * FROM board 
WHERE 
(
	title LIKE CONCAT('%', '1', '%')
	OR content LIKE CONCAT('%', '1', '%')
)
ORDER BY bno DESC;



SELECT * FROM reply;

SELECT * FROM board_image ORDER BY board_bno;
SELECT * FROM board_image_set;


DELETE FROM board_image WHERE board_bno IS NULL;
COMMIT;

DROP TABLE board_image;
DROP TABLE reply;
DROP TABLE board;
