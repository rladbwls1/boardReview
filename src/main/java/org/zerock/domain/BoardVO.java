package org.zerock.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {

	private Long bno;			//게시글 번호
	private String title;		//제목
	private String content;		//내용
	private String writer;		//작성자
	private Date regdate;		//등록일
	private Date updateDate;	//수정일
	
	private int replyCnt;		//댓글 개수

	//첨부파일 처리를 위함
	private List<BoardAttachVO> attachList;	//첨부파일 목록
}
