package org.zerock.domain;

import lombok.Data;

//÷������ ������ ���
@Data
public class BoardAttachVO {

  private String uuid;			//���� ���� �̸�
  private String uploadPath;	//���� ���� ��� 
  private String fileName;		//���� �̸�
  private boolean fileType;		//�̹����� ��� 1, �ƴϸ� 0
  
  private Long bno;				//�Խñ� ��ȣ 
  
}
