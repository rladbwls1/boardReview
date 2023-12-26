package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardMapper {
	//�ֱ� �Խñ� 100�� ��������
	public List<BoardVO> getList();
	//�������� �´� �Խñ� ��������
	public List<BoardVO> getListWithPaging(Criteria cri);
	//�Խñ� ���
	public void insert(BoardVO board);
	//�Խñ� ��� �� bno�� ��ȯ�ޱ� 
	public Integer insertSelectKey(BoardVO board);
	//�ش� �Խñ� �б�
	public BoardVO read(Long bno);
	//�ش� �Խñ� �����ϱ�, ���� ������ 0�ƴ� �� ��ȯ
	public int delete(Long bno);
	//�ش� �Խñ� �����ϱ�, ���� ������ 0�ƴ� �� ��ȯ
	public int update(BoardVO board);
	//�˻� ����� �Խñ� �� �������� 
	public int getTotalCount(Criteria cri);
	//��� ���� ������Ʈ 
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount);
	//�ش� �Խñ� ÷������ ��� ��������
	public List<BoardAttachVO> findByBno(Long bno);

}
 