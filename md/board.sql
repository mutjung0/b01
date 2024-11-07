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