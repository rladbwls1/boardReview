package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardAttachMapper;
import org.zerock.mapper.BoardMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class BoardServiceImpl implements BoardService {

	//vo라서  @Setter?
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;

	@Transactional
	@Override
	public void register(BoardVO board) {
	//DB에 게시글 업로드 
		log.info("register......" + board);
		//seq_board 시퀀스값 받아서 bno에 넣는 시퀀스 후 DB에 게시글 저장
		mapper.insertSelectKey(board);
		//첨부파일 없는 경우 종료 
		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		//첨부파일 있는 경우 DB에 저장 
		board.getAttachList().forEach(attach -> {

			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	//글 번호로 게시글 읽어오기
	@Override
	public BoardVO get(Long bno) {

		log.info("get......" + bno);

		return mapper.read(bno);

	}

	//게시글 수정 
	@Transactional
	@Override
	public boolean modify(BoardVO board) {

		log.info("modify......" + board);
		//해당 게시글의 첨부파일 모두 삭제 
		attachMapper.deleteAll(board.getBno());
		//게시글 수정
		boolean modifyResult = mapper.update(board) == 1;
		//첨부파일이 있으면 첨부파일 등록
		if (modifyResult && board.getAttachList() != null) {
			//해당 파일 꺼내기 
			board.getAttachList().forEach(attach -> {
				//해당 게시글 번호를 파일테이블 bno에 저장
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}

		return modifyResult;
	}


	//게시글 삭제 
	@Transactional
	@Override
	public boolean remove(Long bno) {

		log.info("remove...." + bno);
		//해당 게시글의 첨부파일 삭제
		attachMapper.deleteAll(bno);
		//해당 게시글 삭제 
		return mapper.delete(bno) == 1;
	}


	//게시글 목록 가져오기(페이징 처리와 함께)
	@Override
	public List<BoardVO> getList(Criteria cri) {

		log.info("get List with criteria: " + cri);
		return mapper.getListWithPaging(cri);
	}
	//모든 글 수
	@Override
	public int getTotal(Criteria cri) {

		log.info("get total count");
		return mapper.getTotalCount(cri);
	}
	//해당 게시글의 첨부파일 가져오기 
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {

		log.info("get Attach list by bno" + bno);

		return attachMapper.findByBno(bno);
	}
	//해당 게시글의 첨부파일 모두 없애기 
	@Override
	public void removeAttach(Long bno) {

		log.info("remove all attach files");

		attachMapper.deleteAll(bno);
	}

}
