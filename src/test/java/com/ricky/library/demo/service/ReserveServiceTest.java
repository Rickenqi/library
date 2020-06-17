package com.ricky.library.demo.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricky.library.demo.util.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReserveServiceTest {

    Gson GSON = new GsonBuilder().create();

    @Autowired
    ReserveService reserveService;

    @Test
    void addReserveInfoTest() {
        System.out.println(reserveService.addReserve("22", 10, "test1"));
    }

    @Test
    void getReserveInfoTest() {
        Result result = reserveService.getReserve("test1");
        System.out.println(GSON.toJson(result));
    }

}
