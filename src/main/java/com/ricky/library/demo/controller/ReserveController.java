package com.ricky.library.demo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricky.library.demo.service.ReserveService;
import com.ricky.library.demo.util.result.Result;
import com.ricky.library.demo.util.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReserveController {

    @Autowired
    ReserveService reserveService;

    Gson gson = new GsonBuilder().create();

    @RequestMapping("/reserve")
    String reserveBook(@RequestBody String reader_id, @RequestBody String ISBN, @RequestBody Integer reserve_time) {
        Result result = new Result();
        ResultCode code = reserveService.addReserve(ISBN,reserve_time,reader_id);
        result.setData(ISBN);
        result.setResultCode(code);
        return gson.toJson(result);
    }

    @GetMapping("/reserve")
    String getReserveInfo(@PathVariable String reader_id) {
        Result result = new Result();
        result = reserveService.getReserve(reader_id);
        return gson.toJson(result);
    }
}
