package com.ricky.library.demo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricky.library.demo.domain.Book;
import com.ricky.library.demo.domain.BookList;
import com.ricky.library.demo.service.BookService;
import com.ricky.library.demo.util.result.Result;
import com.ricky.library.demo.util.result.ResultCode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    Gson gson = new GsonBuilder().create();

    @PostMapping("/add_book")
    String addBook(@RequestBody String ISBN, @RequestBody String book_name, @RequestBody String book_author,
            @RequestBody String book_publisher, @RequestBody Date book_pubdate) {
        Book book = new Book();
        book.setBookIsbn(ISBN); book.setBookName(book_name); book.setBookAuthor(book_author);
        book.setBookPublisher(book_publisher); book.setBookPubdate(book_pubdate);
        Result result = new Result();
        ResultCode code = bookService.addBook(book);
        result.setData(book);
        result.setResultCode(code);
        return gson.toJson(result);
    }

    @PostMapping("/del_book")
    String deleteBook(@RequestBody String ISBN) {
        Result result = new Result();
        ResultCode code = bookService.deleteBook(ISBN);
        result.setData(ISBN);
        result.setResultCode(code);
        return gson.toJson(result);
    }

    @PostMapping("/add_list")
    String addBookList(@RequestBody String ISBN, @RequestBody Integer list_id, @RequestBody String list_place) {
        Result result = new Result();
        BookList bookList = new BookList();
        bookList.setListPlace(list_place); bookList.setListId(list_id);
        ResultCode code = bookService.addBookList(bookList,ISBN);
        result.setData(bookList);
        result.setResultCode(code);
        return gson.toJson(result);
    }

    @PostMapping("/del_list")
    String delBookList(@RequestBody Integer list_id) {
        Result result = new Result();
        BookList bookList = new BookList();
        ResultCode code = bookService.deleteBookList(list_id,bookList);
        result.setData(bookList);
        result.setResultCode(code);
        return gson.toJson(result);
    }

    @GetMapping("/book")
    String getBook(@RequestParam String ISBN, @RequestParam String book_name,
                   @RequestParam String book_author, @RequestParam String book_publisher) {
        Book book = new Book();
        book.setBookIsbn(ISBN);
        book.setBookName(book_name);
        book.setBookPublisher(book_publisher);
        book.setBookAuthor(book_author);
        Result result = bookService.getBook(book,1,50);
        return gson.toJson(result);
    }
}

