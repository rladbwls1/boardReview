package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {
	//댓글 등록
	public int insert(ReplyVO vo);
	//게시글 댓글 읽기
	public ReplyVO read(Long bno);
	//해당 댓글 삭제
	public int delete(Long rno);
	//해달 댓글 수정
	public int update(ReplyVO reply);
	//댓글 목록 가져오기 (+페이지 고려)
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
	//해당 게시글 댓글 수 가져오기
	public int getCountByBno(Long bno);
}
