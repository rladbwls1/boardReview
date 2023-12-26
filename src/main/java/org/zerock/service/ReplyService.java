package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;

public interface ReplyService {
	//댓글 등록시 
	public int register(ReplyVO vo);
	//댓글 정보 불러오기 (댓글 클릭시)
	public ReplyVO get(Long rno);
	//댓글 내용과 수정시간 수정
	public int modify(ReplyVO vo);
	//댓글 삭제 
	public int remove(Long rno);
	//게시글의 모든 댓글 가져오기
	public List<ReplyVO> getList(Criteria cri, Long bno);
	//게시글의 댓글 개수와 게시글의 모든 댓글 가져오기
	public ReplyPageDTO getListPage(Criteria cri, Long bno);

}
