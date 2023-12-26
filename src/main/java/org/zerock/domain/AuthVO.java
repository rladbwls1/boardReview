package org.zerock.domain;


import lombok.Data;

@Data
public class AuthVO {

  private String userid;	//유저 아이디
  private String auth;		//유저 권한
  
}
