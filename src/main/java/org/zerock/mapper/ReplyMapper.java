package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {
	//��� ���
	public int insert(ReplyVO vo);
	//�Խñ� ��� �б�
	public ReplyVO read(Long bno);
	//�ش� ��� ����
	public int delete(Long rno);
	//�ش� ��� ����
	public int update(ReplyVO reply);
	//��� ��� �������� (+������ ���)
	public List<ReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);
	//�ش� �Խñ� ��� �� ��������
	public int getCountByBno(Long bno);
}
