package com.ricky.library.demo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricky.library.demo.domain.RentInfo;
import com.ricky.library.demo.service.RentService;
import com.ricky.library.demo.util.result.Result;
import com.ricky.library.demo.util.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RentController {

    @Autowired
    RentService rentService;

    Gson gson = new GsonBuilder().create();

    @PostMapping("/borrow")
    String borrowBook(@RequestBody Integer list_id, @RequestBody String reader_id) {
        Result result = new Result();
        ResultCode code = rentService.rentBook(list_id, reader_id);
        result.setData(list_id);
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
        List<RentInfo> rentInfoList = new ArrayList<>();
        ResultCode code = rentService.getRentInfo(reader_id, rentInfoList);
        Result result = new Result();
        result.setResultCode(code); result.setData(rentInfoList);
        return gson.toJson(result);
    }

}
