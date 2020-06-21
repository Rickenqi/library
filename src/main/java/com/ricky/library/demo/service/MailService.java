package com.ricky.library.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailService {

    @Autowired
    JavaMailSenderImpl mailSender;

    public void sendMailRent(String address, String name, String book, String date) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper help = new MimeMessageHelper(mimeMessage,true);
        help.setSubject("您好！您的图书即将归还");
        help.setText(name+"您好，您的图书"+book+"在"+date+"需要归还");
        help.setTo(address);
        help.setFrom("1553680641@qq.com");
        mailSender.send(mimeMessage);
    }

    public void sendMailReserve(String address, String name, String book) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper help = new MimeMessageHelper(mimeMessage,true);
        help.setSubject("您好！您的图书已预约成功");
        help.setText(name+"您好，您的图书"+book+"已预约成功");
        help.setTo(address);
        help.setFrom("1553680641@qq.com");
        mailSender.send(mimeMessage);
    }
}
