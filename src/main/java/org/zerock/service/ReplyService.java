package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;

public interface ReplyService {
	//��� ��Ͻ� 
	public int register(ReplyVO vo);
	//��� ���� �ҷ����� (��� Ŭ����)
	public ReplyVO get(Long rno);
	//��� ����� �����ð� ����
	public int modify(ReplyVO vo);
	//��� ���� 
	public int remove(Long rno);
	//�Խñ��� ��� ��� ��������
	public List<ReplyVO> getList(Criteria cri, Long bno);
	//�Խñ��� ��� ������ �Խñ��� ��� ��� ��������
	public ReplyPageDTO getListPage(Criteria cri, Long bno);

}
