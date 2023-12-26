package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {

	private Long bno;			//�Խñ� ��ȣ
	private String title;		//����
	private String content;		//����
	private String writer;		//�ۼ���
	private Date regdate;		//�����
	private Date updateDate;	//������
	
	private int replyCnt;		//��� ����

	//÷������ ó���� ����
	private List<BoardAttachVO> attachList;	//÷������ ���
}
