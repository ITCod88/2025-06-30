package com.my.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.my.dto.MyBoardDto;

public class MyBoardDao extends SqlMapConfig{
	//Jsp01_MyBoard와 비교하기 !!
	
	private String namespace = "com.my.myboard";
	
	//전체 출력
	public List<MyBoardDto> selectAll(){
		//준비
		List<MyBoardDto> res = new ArrayList<>();
		SqlSession session = null;
		
										//(true) => autoCommit
		session = getSqlSessionFactory().openSession(true);
		res = session.selectList("com.my.myboard.selectAll");
		
		session.close();
		
		return res;
	}
	
	//선택 출력(myno)
	public MyBoardDto selectOne(int myno) {
		SqlSession session = null;
		MyBoardDto res = null;
		
		try {
			session = getSqlSessionFactory().openSession(true);
			res = session.selectOne(namespace + ".selectOne", myno);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return res;
	}
	
	//글 작성
	public int insert(MyBoardDto dto) {
		SqlSession session = null;
		int res = 0;
		
		session = getSqlSessionFactory().openSession(true);
		res = session.insert(namespace + ".myinsert", dto);
		
		session.close();
		
		return res;
	}
	
}









