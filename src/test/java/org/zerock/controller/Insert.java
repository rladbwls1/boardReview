package org.zerock.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
					"file:src/main/webapp/WEB-INF/spring/security-context.xml"})

public class Insert {
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private DataSource ds;
	@Test
	public void test() {
		String sql="insert into tbl_member(userid,userpw,username) values(?,?,?)";
		for(int i=0;i<100;i++) {
			Connection conn=null;
			PreparedStatement pstmt=null;
			try {
				conn=ds.getConnection();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(2, encoder.encode("pw"+i));
				if(i<80) {
					pstmt.setString(1, "user"+i);
					pstmt.setString(3, "일반사용자"+i);
				}else if(i<90) {
					pstmt.setString(1, "member"+i);
					pstmt.setString(3, "멘토"+i);
				}else {
					pstmt.setString(1, "admin"+i);
					pstmt.setString(3, "운영자"+i);
				}
				pstmt.execute();
			}catch(Exception e) { e.printStackTrace();	
			} finally {
				if(pstmt !=null) {try {pstmt.close();}catch(Exception e) {}}
				if(conn !=null) {try {conn.close();}catch(Exception e) {}}
			}
		}
	}
	@Test
	public void test2() {
		String sql="insert into tbl_member_auth(userid,auth) values(?,?)";
		for(int i=0;i<100;i++) {
			Connection conn=null;
			PreparedStatement pstmt=null;
			try {
				conn=ds.getConnection();
				pstmt=conn.prepareStatement(sql);
				if(i<80) {
					pstmt.setString(1, "user"+i);
					pstmt.setString(2, "ROLE_USER");
				}else if(i<90) {
					pstmt.setString(1, "member"+i);
					pstmt.setString(2, "ROLE_MEMBER");
				}else {
					pstmt.setString(1, "admin"+i);
					pstmt.setString(2, "ROLE_ADMIN");
				}
				pstmt.execute();
			}catch(Exception e) { e.printStackTrace();	
			} finally {
				if(pstmt !=null) {try {pstmt.close();}catch(Exception e) {}}
				if(conn !=null) {try {conn.close();}catch(Exception e) {}}
			}
		}
	}
	
}
