package com.ricky.library.demo.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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

@Data
class BookInput {
    @JsonProperty("ISBN")
    String ISBN;
    @JsonProperty("book_name")
    String book_name;
    @JsonProperty("book_author")
    String book_author;
    @JsonProperty("book_publisher")
    String book_publisher;
    @JsonProperty("book_pubdate")
    @JsonFormat(pattern="yyyy-MM-dd")
    Date book_pubdate;
}

@Data
class ListInput {
    @JsonProperty("ISBN")
    String ISBN;
    @JsonProperty("list_id")
    Integer list_id;
    @JsonProperty("list_place")
    String list_place;
}

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    Gson gson = new GsonBuilder().create();

    @PostMapping(value = "/add_book", consumes = "application/json")
    String addBook(@RequestBody BookInput input) {
        System.out.println(input);
        Book book = new Book();
        book.setBookIsbn(input.getISBN()); book.setBookName(input.getBook_name()); book.setBookAuthor(input.getBook_author());
        book.setBookPublisher(input.getBook_publisher()); book.setBookPubdate(input.getBook_pubdate());
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
    String addBookList(@RequestBody ListInput input) {
        Result result = new Result();
        BookList bookList = new BookList();
        bookList.setListPlace(input.getList_place()); bookList.setListId(input.getList_id());
        ResultCode code = bookService.addBookList(bookList,input.getISBN());
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
    String getBook(@RequestParam(required = false) String ISBN, @RequestParam(required = false) String book_name,
                   @RequestParam(required = false) String book_author, @RequestParam(required = false) String book_publisher) {
        Book book = new Book();
        if(ISBN.equals("")) ISBN = null;
        if(book_name.equals("")) book_name = null;
        if(book_author.equals("")) book_author = null;
        if(book_publisher.equals("")) book_publisher = null;
        book.setBookIsbn(ISBN);
        book.setBookName(book_name);
        book.setBookPublisher(book_publisher);
        book.setBookAuthor(book_author);
        Result result = bookService.getBook(book,1,50);
        return gson.toJson(result);
    }

    @GetMapping("/book_info")
    String getBookInfo(@RequestParam(required = false) String ISBN) {
        Result result = bookService.getBookList(ISBN,null,false);
        return gson.toJson(result);
    }

    @GetMapping("/list_info")
    String getListInfo(@RequestParam(required = false) String ISBN, @RequestParam(required = false) Integer list_id) {
        Result result = bookService.getBookList(ISBN, list_id, true);
        return gson.toJson(result);
    }
}

