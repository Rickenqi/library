package com.ricky.library.demo.service;

import com.ricky.library.demo.domain.Book;
import com.ricky.library.demo.domain.BookList;
import com.ricky.library.demo.domain.ReserveInfo;
import com.ricky.library.demo.domain.example.BookExample;
import com.ricky.library.demo.mapper.BookListMapper;
import com.ricky.library.demo.mapper.BookMapper;
import com.ricky.library.demo.mapper.ReserveInfoMapper;
import com.ricky.library.demo.util.result.Result;
import com.ricky.library.demo.util.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.security.cert.Certificate;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookListMapper bookListMapper;

    @Autowired
    ReserveInfoMapper reserveInfoMapper;

    /**
     * 添加书目信息
     * @param book
     * @return
     */
    public ResultCode addBook(Book book) {
        book.setBookNum(0);
        try {
            bookMapper.insertSelective(book);
        } catch (DataAccessException e) {
            System.out.println(e);
            return ResultCode.INTERFACE_INNER_INVOKE_ERROR;
         }
        return ResultCode.SUCCESS;
    }

    /**
     * 删除书目信息
     * @param book_ISBN
     * @return
     */
    public ResultCode deleteBook(String book_ISBN) {
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

    /**
     * 添加图书信息
     * @param bookList
     * @param book_ISBN
     * @return
     */
    public ResultCode addBookList(BookList bookList, String book_ISBN) {
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

    public ResultCode deleteBookList(Integer listId, BookList bookList) {
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

    public Result getBook(Book book, int pageNum, int pageSize) {
        BookExample bookExample = new BookExample();
        Result result = new Result();
        List<Book> books;
        BookExample.Criteria criteria = bookExample.createCriteria();
        criteria.andBookIdIsNotNull();
        if(book.getBookIsbn() != null)
            criteria.andBookIsbnEqualTo(book.getBookIsbn());
        if(book.getBookName() != null)
            criteria.andBookNameEqualTo(book.getBookName());
        if(book.getBookAuthor() != null)
            criteria.andBookAuthorEqualTo(book.getBookAuthor());
        if(book.getBookPublisher() != null)
            criteria.andBookAuthorEqualTo(book.getBookAuthor());
        bookExample.or(criteria);
        try {
            books = bookMapper.selectByExample(bookExample);
            if(books == null) {
                result.setResultCode(ResultCode.RESULE_DATA_NONE);
                return result;
            }
        } catch (DataAccessException e){
            System.out.println(e);
            result.setResultCode(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
            return result;
        }
        PageHelper.startPage(pageNum, pageSize);
        result.setData(PageInfo.of(books));
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

//    Result getAllBooks(int pageNum, int pageSize) {
//        Result result = new Result();
//        BookExample bookExample = new BookExample();
//        List<Book> books;
//        bookExample.createCriteria().andBookIdIsNotNull();
//        try {
//            books = bookMapper.selectByExample(bookExample);
//            if(books == null) {
//                result.setResultCode(ResultCode.RESULE_DATA_NONE);
//                return result;
//            }
//        } catch (DataAccessException e) {
//            System.out.println(e);
//            result.setResultCode(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
//            return result;
//        }
//        PageHelper.startPage(pageNum, pageSize);
//        result.setData(PageInfo.of(books));
//        result.setResultCode(ResultCode.SUCCESS);
//        return result;
//    }
}
