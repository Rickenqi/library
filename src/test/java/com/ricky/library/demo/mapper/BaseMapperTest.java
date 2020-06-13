package com.ricky.library.demo.mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.Reader;


/**
 * 基础测试类
 */
public class BaseMapperTest {
	private static SqlSessionFactory sqlSessionFactory;

	// 在junit5中请使用BeforeAll
	@BeforeAll
	public static void init(){
		try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            System.out.println("读取完成");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException ignore) {
        	ignore.printStackTrace();
        }
	}
	
	public SqlSession getSqlSession(){
		return sqlSessionFactory.openSession();
	}
	
}
