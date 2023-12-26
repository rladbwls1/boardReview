package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {

	private String userid;		//���̵�
	private String userpw;		//��й�ȣ
	private String userName;	//�̸�
	private boolean enabled;	//Ȱ��

	private Date regDate;		//������
	private Date updateDate;	//������
	private List<AuthVO> authList;	//���� ���

}
