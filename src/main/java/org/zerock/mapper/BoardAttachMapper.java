package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardAttachVO;

public interface BoardAttachMapper {
	//첨부파일 등록
	public void insert(BoardAttachVO vo);
	//해당 첨부파일 삭제
	public void delete(String uuid);
	//게시글의 첨부파일 읽기 
	public List<BoardAttachVO> findByBno(Long bno);
	//게시글의 첨부파일 모두 삭제
	public void deleteAll(Long bno);
	//안 쓰는거 같은데
	public List<BoardAttachVO> getOldFiles();

}