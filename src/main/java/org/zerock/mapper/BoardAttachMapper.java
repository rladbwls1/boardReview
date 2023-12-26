package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardAttachVO;

public interface BoardAttachMapper {
	//÷������ ���
	public void insert(BoardAttachVO vo);
	//�ش� ÷������ ����
	public void delete(String uuid);
	//�Խñ��� ÷������ �б� 
	public List<BoardAttachVO> findByBno(Long bno);
	//�Խñ��� ÷������ ��� ����
	public void deleteAll(Long bno);
	//�� ���°� ������
	public List<BoardAttachVO> getOldFiles();

}