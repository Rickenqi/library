package com.ricky.library.demo.service;

import com.ricky.library.demo.domain.Book;
import com.ricky.library.demo.domain.BookList;
import com.ricky.library.demo.domain.ReserveInfo;
import com.ricky.library.demo.mapper.BookListMapper;
import com.ricky.library.demo.mapper.BookMapper;
import com.ricky.library.demo.mapper.ReserveInfoMapper;
import com.ricky.library.demo.util.result.Result;
import com.ricky.library.demo.util.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookListMapper bookListMapper;

    @Autowired
    ReserveInfoMapper reserveInfoMapper;

    ResultCode addBook(Book book) {
        book.setBookNum(0);
        try {
            bookMapper.insertSelective(book);
        } catch (DataAccessException e) {
            System.out.println(e);
            return ResultCode.INTERFACE_INNER_INVOKE_ERROR;
         }
        return ResultCode.SUCCESS;
    }

    ResultCode deleteBook(String book_ISBN) {
        Book book;
        try {
            book = bookMapper.selectByISBN(book_ISBN);
            if(book == null)
                return ResultCode.RESULE_DATA_NONE;
            bookMapper.deleteByPrimaryKey(book.getBookId());
        } catch (DataAccessException e) {
            System.out.println(e);
            return ResultCode.INTERFACE_INNER_INVOKE_ERROR;
        }
        return ResultCode.SUCCESS;
    }

    ResultCode addBookList(BookList bookList, String book_ISBN) {
        Book book;
        try {
            book = bookMapper.selectByISBN(book_ISBN);
            if(book == null)
                return ResultCode.DATA_IS_WRONG;
            bookList.setBookId(book.getBookId());
            bookList.setListState("在库");
            book.setBookNum(book.getBookNum()+1);
            bookMapper.updateByPrimaryKeySelective(book);
            int list_id = bookListMapper.insertSelective(bookList);
            // 更新预约情况
            List<ReserveInfo> reserveInfos = reserveInfoMapper.selectByBookId(book.getBookId());
            for (ReserveInfo r: reserveInfos) {
                if (r.getReserveAgent().equals("等待")) {
                    r.setReserveAgent("成功");
                    r.setListId(list_id);
                    reserveInfoMapper.updateByPrimaryKeySelective(r);
                    bookList.setListState("借出");
                    bookListMapper.updateByPrimaryKeySelective(bookList);
                    break;
                }
            }
        } catch (DataAccessException e) {
            System.out.println(e);
            return ResultCode.INTERFACE_INNER_INVOKE_ERROR;
        }
        return ResultCode.SUCCESS;
    }

    ResultCode deleteBookList(Integer listId, BookList bookList) {
        try {
            bookList = bookListMapper.selectByPrimaryKey(listId);
            if(bookList == null)
                return ResultCode.DATA_IS_WRONG;
            bookListMapper.deleteByPrimaryKey(listId);
        } catch (DataAccessException e) {
            System.out.println(e);
            return ResultCode.INTERFACE_INNER_INVOKE_ERROR;
        }
        return ResultCode.SUCCESS;
    }

}
