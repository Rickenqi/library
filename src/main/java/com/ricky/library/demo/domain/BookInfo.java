package com.ricky.library.demo.domain;

import com.ricky.library.demo.domain.Book;
import com.ricky.library.demo.domain.BookList;
import com.ricky.library.demo.domain.ReserveInfo;
import lombok.Data;

import java.util.List;

@Data
public class BookInfo {
    Book book;
    List<BookList> bookLists;
    List<RentInfo> RentInfoList;
}
