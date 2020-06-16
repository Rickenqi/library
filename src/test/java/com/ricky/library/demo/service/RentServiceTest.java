package com.ricky.library.demo.service;

import com.ricky.library.demo.domain.RentInfo;
import com.ricky.library.demo.util.result.ResultCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class RentServiceTest {

    @Autowired
    RentService rentService;

    @Test
    public void testRentBook() {
        ResultCode resultCode = rentService.rentBook(1,"test1");
        System.out.println(resultCode);
    }

    @Test
    public void testReturnBook() {
        ResultCode resultCode = rentService.returnBook(1);
        System.out.println(resultCode);
    }

    @Test
    public void testRentInfo() {
        List<RentInfo> rentInfo = new ArrayList<>();
        rentService.getRentInfo("test1", rentInfo);
        for (RentInfo r: rentInfo) {
            System.out.println(r);
        }
    }
}
