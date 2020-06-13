package com.ricky.library.demo.mapper;

import com.ricky.library.demo.domain.Book;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.List;

public class BookMapperTest extends BaseMapperTest {
    @Test
    public void testSelectByPrimaryKey(){
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            Book book = bookMapper.selectByPrimaryKey("1");
            Assert.assertNotNull(book);
        } finally {
            //不要忘记关闭 sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testInsert(){
        SqlSession sqlSession = getSqlSession();
        try {
            BookMapper bookMapper = sqlSession.getMapper(BookMapper.class);
            Book book = new Book();
            book.setBookId("1");
            //将新建的对象插入数据库中，特别注意，这里的返回值 result 是执行的 SQL 影响的行数
            int result = bookMapper.insert(book);
            //只插入 1 条数据
            org.testng.Assert.assertEquals(1, result);
        } finally {
            //为了不影响数据库中的数据导致其他测试失败，这里选择回滚
            //由于默认的 sqlSessionFactory.openSession() 是不自动提交的，
            //因此不手动执行 commit 也不会提交到数据库
            sqlSession.rollback();
            //不要忘记关闭 sqlSession
            sqlSession.close();
        }
    }
}
