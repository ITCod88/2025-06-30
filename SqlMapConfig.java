package com.my.dao;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlMapConfig {
	private SqlSessionFactory sqlSessionFactory;
	
	public SqlSessionFactory getSqlSessionFactory() {
		
		//Config파일의 경로
		String resource = "com/my/db/Config.xml";
		
		try {
			//Config.xml의 파일 정보를 읽어온다.
			Reader reader = Resources.getResourceAsReader(resource);
			
			//읽어온 정보를 가지고 SqlSessionFactory객체 생성
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sqlSessionFactory;
	}
}


//SSFB(SqlSessionFactoryBuilder) -> SSF(SqlSessionFactory) -> SS(SqlSession) 











