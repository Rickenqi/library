package com.ricky.library.demo.mapper;

import com.ricky.library.demo.domain.Book;
import com.ricky.library.demo.domain.BookList;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.List;

public class BookListMapperTest extends BaseMapperTest {
    @Test
    public void testSelectByPrimaryKey(){
        //获取 sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            BookListMapper bookListMapper = sqlSession.getMapper(BookListMapper.class);
            List<BookList> books = bookListMapper.selectByBookId(1);
            Assert.assertNotNull(books);
        } finally {
            //不要忘记关闭 sqlSession
            sqlSession.close();
        }
    }
}
