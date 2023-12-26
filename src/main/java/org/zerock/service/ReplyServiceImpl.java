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

	//@setter ������̼ǿ� @Autowired ������̼��� �����Ϸ��� �̷��� ��
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;

	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;

	//��� ��Ͻ� 
	@Transactional
	@Override
	public int register(ReplyVO vo) {

		log.info("register......" + vo);
		//�Խñ��� ��ۼ� �ø�
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		//DB�� ��� �߰� (��۹�ȣ, �Խñ۹�ȣ, ��� ����, ��� �ۼ���)
		return mapper.insert(vo);

	}
	//��� ���� �ҷ����� (��� Ŭ����)
	@Override
	public ReplyVO get(Long rno) {

		log.info("get......" + rno);

		return mapper.read(rno);

	}
	//��� ����� �����ð� ����
	@Override
	public int modify(ReplyVO vo) {

		log.info("modify......" + vo);

		return mapper.update(vo);

	}

	//��� ���� 
	@Transactional
	@Override
	public int remove(Long rno) {

		log.info("remove...." + rno);

		ReplyVO vo = mapper.read(rno);
		//�Խñ� ��� �� ����
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		//DB���� ��� ���� 
		return mapper.delete(rno);

	}

	//�Խñ��� ��� ��� ��������
	@Override
	public List<ReplyVO> getList(Criteria cri, Long bno) {

		log.info("get Reply List of a Board " + bno);

		return mapper.getListWithPaging(cri, bno);

	}

	//�Խñ��� ��� ������ �Խñ��� ��� ��� ��������
	@Override
	public ReplyPageDTO getListPage(Criteria cri, Long bno) {

		return new ReplyPageDTO(mapper.getCountByBno(bno), mapper.getListWithPaging(cri, bno));
	}

}
