package com.ricky.library.demo.mapper;

import com.ricky.library.demo.domain.BookList;
import com.ricky.library.demo.domain.RentInfo;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.List;

public class RentInfoMapperTest extends BaseMapperTest {
    @Test
    public void testSelectByPrimaryKey(){
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            RentInfoMapper rentInfoMapper = sqlSession.getMapper(RentInfoMapper.class);
            RentInfo rentInfo = rentInfoMapper.selectByPrimaryKey(1);
            // Assert.assertNotNull(books);
        } finally {
            //不要忘记关闭 sqlSession
            sqlSession.close();
        }
    }
}
