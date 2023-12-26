package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	//최근 게시글 100개 가져오기
	public List<BoardVO> getList();
	//페이지에 맞는 게시글 가져오기
	public List<BoardVO> getListWithPaging(Criteria cri);
	//게시글 등록
	public void insert(BoardVO board);
	//게시글 등록 후 bno값 반환받기 
	public Integer insertSelectKey(BoardVO board);
	//해당 게시글 읽기
	public BoardVO read(Long bno);
	//해당 게시글 삭제하기, 삭제 성공시 0아닌 값 반환
	public int delete(Long bno);
	//해당 게시글 수정하기, 수정 성공시 0아닌 값 반환
	public int update(BoardVO board);
	//검색 고려한 게시글 수 가져오기 
	public int getTotalCount(Criteria cri);
	//댓글 개수 업데이트 
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
	//해당 게시글 첨부파일 목록 가져오기
	public List<BoardAttachVO> findByBno(Long bno);

}
 