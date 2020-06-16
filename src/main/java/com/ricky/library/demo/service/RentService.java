package com.ricky.library.demo.service;

import com.ricky.library.demo.domain.Book;
import com.ricky.library.demo.domain.BookList;
import com.ricky.library.demo.domain.RentInfo;
import com.ricky.library.demo.mapper.BookListMapper;
import com.ricky.library.demo.mapper.BookMapper;
import com.ricky.library.demo.mapper.RentInfoMapper;
import com.ricky.library.demo.util.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class RentService {

    @Autowired
    RentInfoMapper rentInfoMapper;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookListMapper bookListMapper;

    /**
     * 借书业务
     * @param ListId
     * @param readerId
     * @return
     */
    ResultCode rentBook(Integer ListId, String readerId) {
        Book book;
        BookList bookList;
        RentInfo rentInfo = new RentInfo();
        // 生成借书日期，还书日期
        Date borrow_date = new Date();
        Date return_date;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(borrow_date);
        calendar.add(calendar.DATE,10);
        return_date = calendar.getTime();
        // System.out.println("ok");
        try {
            bookList = bookListMapper.selectByPrimaryKey(ListId);
            if(bookList == null)
                return  ResultCode.RESULE_DATA_NONE;
            book = bookMapper.selectByPrimaryKey(bookList.getBookId());
            if(bookList == null)
                return  ResultCode.RESULE_DATA_NONE;
            rentInfo.setReaderId(readerId); rentInfo.setListId(bookList.getListId()); rentInfo.setBookId(bookList.getBookId());
            rentInfo.setBorrowDate(borrow_date); rentInfo.setReturnDate(return_date); rentInfo.setBookIsbn(book.getBookIsbn());
            rentInfoMapper.insertSelective(rentInfo);
            bookList.setListState("借出");
            bookListMapper.updateByPrimaryKeySelective(bookList);
        } catch (DataAccessException e) {
            System.out.println(e);
            return ResultCode.INTERFACE_INNER_INVOKE_ERROR;
        }
        return ResultCode.SUCCESS;
    }

    /**
     * 还书业务
     * @param ListId
     * @return
     */
    ResultCode returnBook(Integer ListId) {
        BookList bookList;
        try {
            bookList = bookListMapper.selectByPrimaryKey(ListId);
            if(bookList == null)
                return  ResultCode.RESULE_DATA_NONE;
            bookList.setListState("在库");
            bookListMapper.updateByPrimaryKeySelective(bookList);
        } catch (DataAccessException e) {
            System.out.println(e);
            return ResultCode.INTERFACE_INNER_INVOKE_ERROR;
        }
        return ResultCode.SUCCESS;
    }

    /**
     * 查询业务
     * @param readerId
     * @param rentInfo
     * @return
     */
    ResultCode getRentInfo(String readerId, List<RentInfo> rentInfo) {
        try {
           // 需要引用传值请用addAll
           rentInfo.addAll(rentInfoMapper.selectByReaderId(readerId));
        } catch (DataAccessException e) {
            System.out.println(e);
            return ResultCode.INTERFACE_INNER_INVOKE_ERROR;
        }
        return ResultCode.SUCCESS;
    }
}
