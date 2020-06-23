package com.ricky.library.demo.service;

import com.ricky.library.demo.domain.*;
import com.ricky.library.demo.mapper.*;
import com.ricky.library.demo.util.result.Result;
import com.ricky.library.demo.util.result.ResultCode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.*;

@Data
class RentBookInfo {
    Book book;
    RentInfo rentInfo;
}


@Transactional(rollbackFor = DataAccessException.class)
@Service
public class RentService {

    @Autowired
    RentInfoMapper rentInfoMapper;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookListMapper bookListMapper;

    @Autowired
    ReserveInfoMapper reserveInfoMapper;

    @Autowired
    MailService mailService;

    @Autowired
    ReaderMapper readerMapper;

    void updateReserveInfo(Integer bookId) {
        List<ReserveInfo> reserveInfos = reserveInfoMapper.selectByBookId(bookId);
        for (ReserveInfo r: reserveInfos) {
            if(r.getReserveAgent().equals("等待"))
                r.setReserveAgent("成功");
            return;
        }
    }

    /**
     * 借书业务
     * @param ListId 所借图书id
     * @param readerId 所借读者id
     * @return ResultCode 状态码
     */
    public ResultCode rentBook(Integer ListId, String readerId) throws MessagingException {
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
            if(bookList.getListState().equals("借出"))
                return  ResultCode.DATA_ALREADY_EXISTED;
            rentInfo.setReaderId(readerId); rentInfo.setListId(bookList.getListId()); rentInfo.setBookId(bookList.getBookId());
            rentInfo.setBorrowDate(borrow_date); rentInfo.setReturnDate(return_date); rentInfo.setBookIsbn(book.getBookIsbn());
            rentInfoMapper.insertSelective(rentInfo);
            bookList.setListState("借出");
            bookListMapper.updateByPrimaryKeySelective(bookList);
        } catch (DataAccessException e) {
            System.out.println(e);
            return ResultCode.INTERFACE_INNER_INVOKE_ERROR;
        }
        Reader reader = readerMapper.selectByPrimaryKey(readerId);
        mailService.sendMailRent(reader.getReaderEmail(),readerId,book.getBookName(),return_date.toString());
        return ResultCode.SUCCESS;
    }

    /**
     * 还书业务
     * @param ListId 图书编号
     * @return ResultCode状态码
     */
    public ResultCode returnBook(Integer ListId) {
        BookList bookList;
        try {
            bookList = bookListMapper.selectByPrimaryKey(ListId);
            if(bookList == null)
                return  ResultCode.RESULE_DATA_NONE;
            if(bookList.getListState().equals("在库"))
                return ResultCode.DATA_ALREADY_EXISTED;
            bookList.setListState("在库");
            bookListMapper.updateByPrimaryKeySelective(bookList);
            // 更新预约情况
            List<ReserveInfo> reserveInfos = reserveInfoMapper.selectByBookId(bookList.getBookId());
            for (ReserveInfo r: reserveInfos) {
                if (r.getReserveAgent().equals("等待")) {
                    r.setReserveAgent("成功");
                    r.setListId(ListId);
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
     * 查询业务
     * @param readerId 读者id
     * @return ResultCode状态码
     */
    public Result getRentInfo(String readerId) {
        List<RentInfo> rentInfoList;
        List<RentBookInfo> infoList = new ArrayList<>();
        Result result = new Result();
        try {
           rentInfoList = rentInfoMapper.selectByReaderId(readerId);
           for (RentInfo rentInfo: rentInfoList) {
               Book book = bookMapper.selectByPrimaryKey(rentInfo.getBookId());
               RentBookInfo rentBookInfo = new RentBookInfo();
               rentBookInfo.setBook(book); rentBookInfo.setRentInfo(rentInfo);
               infoList.add(rentBookInfo);
           }
        } catch (DataAccessException e) {
            System.out.println(e);
            return Result.failure(ResultCode.INTERFACE_OUTTER_INVOKE_ERROR);
        }
        result.setData(infoList);
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }
}
