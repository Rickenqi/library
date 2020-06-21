package com.ricky.library.demo.service;

import com.ricky.library.demo.domain.*;
import com.ricky.library.demo.mapper.BookListMapper;
import com.ricky.library.demo.mapper.BookMapper;
import com.ricky.library.demo.mapper.ReaderMapper;
import com.ricky.library.demo.mapper.ReserveInfoMapper;
import com.ricky.library.demo.util.result.Result;
import com.ricky.library.demo.util.result.ResultCode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Data
class ReserveBookInfo {
    Book book;
    ReserveInfo reserveInfo;
}

@Service
public class ReserveService {

    @Autowired
    ReserveInfoMapper reserveInfoMapper;

    @Autowired
    BookMapper bookMapper;
    
    @Autowired
    BookListMapper bookListMapper;

    @Autowired
    ReaderMapper readerMapper;

    @Autowired
    MailService mailService;

    void updateReserveInfo(Integer bookId) {
        List<ReserveInfo> reserveInfos = reserveInfoMapper.selectByBookId(bookId);
        for (ReserveInfo r: reserveInfos) {
            if(r.getReserveAgent().equals("等待"))
                r.setReserveAgent("成功");
            return;
        }
    }

    /**
     * 新建预约
     * @param book_ISBN 图书ISBN号
     * @param days 预约天数
     * @param readerId 预约读者Id
     * @return
     */
    public ResultCode addReserve(String book_ISBN, int days, String readerId) throws MessagingException {
        Book book;
        ReserveInfo reserveInfo = new ReserveInfo();
        // 生成借书日期，还书日期
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,days);
        date = calendar.getTime();
        reserveInfo.setReserveAgent("等待");
        reserveInfo.setRentLimit(date);
        reserveInfo.setReaderId(readerId);
        reserveInfo.setBookIsbn(book_ISBN);
        try {
            book = bookMapper.selectByISBN(book_ISBN);
            if(book == null)
                return ResultCode.RESULE_DATA_NONE;
            reserveInfo.setBookId(book.getBookId());
            List<BookList> books = bookListMapper.selectByBookId(book.getBookId());
            for (BookList b: books) {
                if(b.getListState().equals("在库")) {
                    reserveInfo.setReserveAgent("成功");
                    reserveInfo.setListId(b.getListId());
                    b.setListState("借出");
                    bookListMapper.updateByPrimaryKeySelective(b);
                    break;
                }
            }
            reserveInfoMapper.insertSelective(reserveInfo);
        } catch (DataAccessException e) {
            System.out.println(e);
            return ResultCode.INTERFACE_INNER_INVOKE_ERROR;
        }
        Reader reader = readerMapper.selectByPrimaryKey(readerId);
        mailService.sendMailReserve(reader.getReaderEmail(),readerId,book.getBookName());
        return ResultCode.SUCCESS;
    }

    /**
     * 获取预约信息
     * @param readerId 读者
     * @return Result是返回数据和状态码的集合
     */
    public Result getReserve(String readerId ) {
        Result result = new Result();
        List<ReserveInfo> reserveInfoList;
        List<ReserveBookInfo> infoList = new ArrayList<>();
        try {
            reserveInfoList = reserveInfoMapper.selectByReaderId(readerId);
            for (ReserveInfo info: reserveInfoList) {
                Book book = bookMapper.selectByPrimaryKey(info.getBookId());
                ReserveBookInfo bookInfo = new ReserveBookInfo();
                bookInfo.setBook(book); bookInfo.setReserveInfo(info);
                infoList.add(bookInfo);
            }
        } catch (DataAccessException e) {
            System.out.println(e);
            return Result.failure(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
        result.setData(infoList);
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

}
