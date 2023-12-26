package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplyServiceImpl implements ReplyService {

	//@setter 어노테이션에 @Autowired 어노테이션을 지정하려고 이렇게 씀
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;

	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;

	//댓글 등록시 
	@Transactional
	@Override
	public int register(ReplyVO vo) {

		log.info("register......" + vo);
		//게시글의 댓글수 올림
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		//DB에 댓글 추가 (댓글번호, 게시글번호, 댓글 내용, 댓글 작성자)
		return mapper.insert(vo);

	}
	//댓글 정보 불러오기 (댓글 클릭시)
	@Override
	public ReplyVO get(Long rno) {

		log.info("get......" + rno);

		return mapper.read(rno);

	}
	//댓글 내용과 수정시간 수정
	@Override
	public int modify(ReplyVO vo) {

		log.info("modify......" + vo);

		return mapper.update(vo);

	}

	//댓글 삭제 
	@Transactional
	@Override
	public int remove(Long rno) {

		log.info("remove...." + rno);

		ReplyVO vo = mapper.read(rno);
		//게시글 댓글 수 감소
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		//DB에서 댓글 삭제 
		return mapper.delete(rno);

	}

	//게시글의 모든 댓글 가져오기
	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {

		log.info("get Reply List of a Board " + bno);

		return mapper.getListWithPaging(cri, bno);

	}

	//게시글의 댓글 개수와 게시글의 모든 댓글 가져오기
	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {

		return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(cri, bno));
	}

}
