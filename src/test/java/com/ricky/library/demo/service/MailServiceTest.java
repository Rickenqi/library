package com.ricky.library.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    void sendMailTest() throws MessagingException {
        // mailService.sendMail("1448232282@qq.com","Mr.quin","肥仔传");
    }
}
