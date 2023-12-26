package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	//게시글 업로드
	public void register(BoardVO board);
	//글 번호로 게시글 읽어오기
	public BoardVO get(Long bno);
	//게시글 수정
	public boolean modify(BoardVO board);
	//게시글 삭제 
	public boolean remove(Long bno);

	//게시글 목록 가져오기(페이징 처리와 함께)
	public List<BoardVO> getList(Criteria cri);
	//모든 글 수
	public int getTotal(Criteria cri);
	//해당 게시글의 첨부파일 가져오기
	public List<BoardAttachVO> getAttachList(Long bno);
	//해당 게시글의 첨부파일 모두 없애기
	public void removeAttach(Long bno);

}
