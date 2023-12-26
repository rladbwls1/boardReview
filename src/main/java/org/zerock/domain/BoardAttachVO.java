package org.zerock.domain;

import lombok.Data;

//첨부파일 열람시 사용
@Data
public class BoardAttachVO {

  private String uuid;			//파일 저장 이름
  private String uploadPath;	//파일 저장 경로 
  private String fileName;		//파일 이름
  private boolean fileType;		//이미지인 경우 1, 아니면 0
  
  private Long bno;				//게시글 변호 
  
}
