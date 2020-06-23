package com.ricky.library.demo.service;

import com.ricky.library.demo.domain.*;
import com.ricky.library.demo.domain.example.BookExample;
import com.ricky.library.demo.domain.example.BookListExample;
import com.ricky.library.demo.domain.example.RentInfoExample;
import com.ricky.library.demo.domain.example.ReserveInfoExample;
import com.ricky.library.demo.mapper.BookListMapper;
import com.ricky.library.demo.mapper.BookMapper;
import com.ricky.library.demo.mapper.RentInfoMapper;
import com.ricky.library.demo.mapper.ReserveInfoMapper;
import com.ricky.library.demo.util.result.Result;
import com.ricky.library.demo.util.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = DataAccessException.class)
public class BookService {
    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookListMapper bookListMapper;

    @Autowired
    ReserveInfoMapper reserveInfoMapper;

    @Autowired
    RentInfoMapper rentInfoMapper;

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

    /**
     * 删除图书信息
     * @param listId
     * @param bookList
     * @return
     */
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

    /**
     * 获取图书目录信息
     * @param book
     * @param pageNum
     * @param pageSize
     * @return
     */
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
        PageHelper.startPage(pageNum,pageSize);
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
        result.setData(PageInfo.of(books));
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    /**
     * 获取图书详细信息
     * @param book_ISBN
     * @param list_id
     * @param res
     * @return
     */
    public Result getBookList(String book_ISBN, Integer list_id, boolean res) {
        BookListExample bookListExample = new BookListExample();
        Result result = new Result();
        Book book;
        List<BookList> bookLists;
        List<RentInfo> rentInfoList = new ArrayList<>();
        try {
            book = bookMapper.selectByISBN(book_ISBN);
            if(book == null)
                return Result.failure(ResultCode.DATA_IS_WRONG);
        } catch (DataAccessException e){
            System.out.println(e);
            result.setResultCode(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
            return result;
        }
        bookListExample.createCriteria().andBookIdEqualTo(book.getBookId());
        try {
            bookLists = bookListMapper.selectByExample(bookListExample);
        } catch (DataAccessException e){
            System.out.println(e);
            result.setResultCode(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
            return result;
        }
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBook(book); bookInfo.setBookLists(bookLists);
        if(res) {
            RentInfoExample example = new RentInfoExample();
            if (list_id != null) {
                example.createCriteria().andListIdEqualTo(list_id.toString());
                rentInfoList = rentInfoMapper.selectByExample(example);
            }
            else {
                for (BookList bookList : bookLists) {
                    example.createCriteria().andListIdEqualTo(bookList.getBookId().toString());
                    rentInfoList.addAll(rentInfoMapper.selectByExample(example));
                }
            }
            bookInfo.setRentInfoList(rentInfoList);
        }
        result.setData(bookInfo);
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
