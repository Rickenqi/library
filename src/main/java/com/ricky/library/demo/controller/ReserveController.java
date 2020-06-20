package com.ricky.library.demo.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricky.library.demo.service.ReserveService;
import com.ricky.library.demo.util.result.Result;
import com.ricky.library.demo.util.result.ResultCode;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Data
class ReserveInput {
    @JsonProperty("reader_id")
    String reader_id;
    @JsonProperty("ISBN")
    String ISBN;
    @JsonProperty("reserve_time")
    Integer reserve_time;
}

@RestController
public class ReserveController {

    @Autowired
    ReserveService reserveService;

    Gson gson = new GsonBuilder().create();

    @RequestMapping("/reserve")
    String reserveBook(@RequestBody ReserveInput input) {
        Result result = new Result();
        ResultCode code = reserveService.addReserve(input.getISBN(),input.getReserve_time(),input.getReader_id());
        result.setData(input.getISBN());
        result.setResultCode(code);
        return gson.toJson(result);
    }

    @GetMapping("/reserve_info")
    String getReserveInfo(@RequestParam String reader_id) {
        Result result = new Result();
        result = reserveService.getReserve(reader_id);
        return gson.toJson(result);
    }
}
