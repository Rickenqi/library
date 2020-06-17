package com.ricky.library.demo.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricky.library.demo.domain.Book;
import com.ricky.library.demo.domain.BookList;
import com.ricky.library.demo.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    BookService bookService;

    Gson GSON = new GsonBuilder().create();

    @Test
    void addBookTest() {
        Book book = new Book();
        book.setBookName("肥仔传"); book.setBookAuthor("Mr.quin");
        book.setBookPublisher("秦川鸡盒王出版社");book.setBookIsbn("22");
        System.out.println(bookService.addBook(book));
    }

    @Test
    void addBookList() {
        BookList bookList = new BookList();
        System.out.println(bookService.addBookList(bookList,"22"));
    }

    @Test
    void deleteBookList() {
        BookList bookList = new BookList();
        System.out.println(bookService.deleteBookList(15, bookList));
    }

    @Test
    void deleteBookTest() {
        System.out.println(bookService.deleteBook("22"));
    }

//    @Test
//    void getAllBookTest() {
//        System.out.println(GSON.toJson(bookService.getAllBooks(1, 50)));
//    }

    @Test
    void getBookTest() {
        Book book = new Book();
        System.out.println(GSON.toJson(bookService.getBook(book,1,50)));
        book.setBookIsbn("22");
        System.out.println(GSON.toJson(bookService.getBook(book,1,50)));
        book.setBookAuthor("吴军");
        System.out.println(GSON.toJson(bookService.getBook(book,1,50)));
    }
}
