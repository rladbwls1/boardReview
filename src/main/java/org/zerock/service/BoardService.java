package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	//�Խñ� ���ε�
	public void register(BoardVO board);
	//�� ��ȣ�� �Խñ� �о����
	public BoardVO get(Long bno);
	//�Խñ� ����
	public boolean modify(BoardVO board);
	//�Խñ� ���� 
	public boolean remove(Long bno);

	//�Խñ� ��� ��������(����¡ ó���� �Բ�)
	public List<BoardVO> getList(Criteria cri);
	//��� �� ��
	public int getTotal(Criteria cri);
	//�ش� �Խñ��� ÷������ ��������
	public List<BoardAttachVO> getAttachList(Long bno);
	//�ش� �Խñ��� ÷������ ��� ���ֱ�
	public void removeAttach(Long bno);

}
