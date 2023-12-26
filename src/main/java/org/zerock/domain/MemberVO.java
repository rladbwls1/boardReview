package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {

	private String userid;		//아이디
	private String userpw;		//비밀번호
	private String userName;	//이름
	private boolean enabled;	//활동

	private Date regDate;		//가입일
	private Date updateDate;	//수정일
	private List<AuthVO> authList;	//권한 목록

}
