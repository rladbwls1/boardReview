package org.zerock.mapper;

import org.zerock.domain.MemberVO;

public interface MemberMapper {
	//회원 정보 읽기 (권한 포함)
	public MemberVO read(String userid);
}
