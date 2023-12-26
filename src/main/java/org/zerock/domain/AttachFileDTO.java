package org.zerock.domain;

import lombok.Data;

//첨부파일 등록 시 사용 
@Data
public class AttachFileDTO {

	private String fileName;		//파일 이름
	private String uploadPath;		//파일 저장 경로
	private String uuid;			//저장할 이름
	private boolean image;			//이미지 판단

}
