package org.zerock.mapper;

import org.zerock.domain.MemberVO;

public interface MemberMapper {
	//ȸ�� ���� �б� (���� ����)
	public MemberVO read(String userid);
}
