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

	//vo��  @Setter?
	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	@Setter(onMethod_ = @Autowired)
	private BoardAttachMapper attachMapper;

	@Transactional
	@Override
	public void register(BoardVO board) {
	//DB�� �Խñ� ���ε� 
		log.info("register......" + board);
		//seq_board �������� �޾Ƽ� bno�� �ִ� ������ �� DB�� �Խñ� ����
		mapper.insertSelectKey(board);
		//÷������ ���� ��� ���� 
		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		//÷������ �ִ� ��� DB�� ���� 
		board.getAttachList().forEach(attach -> {

			attach.setBno(board.getBno());
			attachMapper.insert(attach);
		});
	}

	//�� ��ȣ�� �Խñ� �о����
	@Override
	public BoardVO get(Long bno) {

		log.info("get......" + bno);

		return mapper.read(bno);

	}

	//�Խñ� ���� 
	@Transactional
	@Override
	public boolean modify(BoardVO board) {

		log.info("modify......" + board);
		//�ش� �Խñ��� ÷������ ��� ���� 
		attachMapper.deleteAll(board.getBno());
		//�Խñ� ����
		boolean modifyResult = mapper.update(board) == 1;
		//÷�������� ������ ÷������ ���
		if (modifyResult && board.getAttachList() != null) {
			//�ش� ���� ������ 
			board.getAttachList().forEach(attach -> {
				//�ش� �Խñ� ��ȣ�� �������̺� bno�� ����
				attach.setBno(board.getBno());
				attachMapper.insert(attach);
			});
		}

		return modifyResult;
	}


	//�Խñ� ���� 
	@Transactional
	@Override
	public boolean remove(Long bno) {

		log.info("remove...." + bno);
		//�ش� �Խñ��� ÷������ ����
		attachMapper.deleteAll(bno);
		//�ش� �Խñ� ���� 
		return mapper.delete(bno) == 1;
	}


	//�Խñ� ��� ��������(����¡ ó���� �Բ�)
	@Override
	public List<BoardVO> getList(Criteria cri) {

		log.info("get List with criteria: " + cri);
		return mapper.getListWithPaging(cri);
	}
	//��� �� ��
	@Override
	public int getTotal(Criteria cri) {

		log.info("get total count");
		return mapper.getTotalCount(cri);
	}
	//�ش� �Խñ��� ÷������ �������� 
	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {

		log.info("get Attach list by bno" + bno);

		return attachMapper.findByBno(bno);
	}
	//�ش� �Խñ��� ÷������ ��� ���ֱ� 
	@Override
	public void removeAttach(Long bno) {

		log.info("remove all attach files");

		attachMapper.deleteAll(bno);
	}

}
