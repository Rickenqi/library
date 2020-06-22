package com.ricky.library.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailService {

    @Autowired
    JavaMailSenderImpl mailSender;

    /**
     * 发送还书提醒邮件
     * @param address 邮件地址
     * @param name 用户名
     * @param book 书名
     * @param date 还书日期
     * @throws MessagingException
     */
    @Scheduled(initialDelay = 1000)
    public void sendMailRent(String address, String name, String book, String date) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper help = new MimeMessageHelper(mimeMessage,true);
        help.setSubject("您好！您的图书即将归还");
        help.setText(name+"您好，您的图书"+book+"在"+date+"需要归还");
        help.setTo(address);
        help.setFrom("1553680641@qq.com");
        mailSender.send(mimeMessage);
    }

    /**
     * 发送预约提醒邮件
     * @param address
     * @param name
     * @param book
     * @throws MessagingException
     */
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
