package com.ricky.library.demo.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricky.library.demo.domain.RentInfo;
import com.ricky.library.demo.service.RentService;
import com.ricky.library.demo.util.result.Result;
import com.ricky.library.demo.util.result.ResultCode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Data
class BorrowInput {
    @JsonProperty("list_id")
    Integer list_id;
    @JsonProperty("reader_id")
    String reader_id;
}

@RestController
public class RentController {

    @Autowired
    RentService rentService;

    Gson gson = new GsonBuilder().create();

    @PostMapping("/borrow")
    String borrowBook(@RequestBody BorrowInput input) {
        Result result = new Result();
        ResultCode code = rentService.rentBook(input.getList_id(), input.getReader_id());
        result.setData(input.getList_id());
        result.setResultCode(code);
        return gson.toJson(result);
    }

    @PostMapping("/return")
    String returnBook(@RequestBody Integer list_id) {
        Result result = new Result();
        ResultCode code = rentService.returnBook(list_id);
        result.setData(list_id);
        result.setResultCode(code);
        return gson.toJson(result);
    }

    @GetMapping("/rent_info")
    String getRentInfo(@RequestParam String reader_id) {
        Result result = rentService.getRentInfo(reader_id);
        return gson.toJson(result);
    }

}
