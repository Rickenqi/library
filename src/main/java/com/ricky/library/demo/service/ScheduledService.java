package com.ricky.library.demo.service;

import com.ricky.library.demo.domain.ReserveInfo;
import com.ricky.library.demo.domain.example.ReserveInfoExample;
import com.ricky.library.demo.mapper.ReserveInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = DataAccessException.class)
@Component
public class ScheduledService {

    Date date=new Date();
    SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");

    @Autowired
    ReserveInfoMapper reserveInfoMapper;

    @Scheduled(cron = "*/5 * * * * ?")
    public void execute() {
        System.out.println(date);
        ReserveInfoExample example = new ReserveInfoExample();
        example.createCriteria().andRentLimitEqualTo(dateFormat.format(date));
        // example.createCriteria().andRentLimitEqualTo("2020-06-29");
        try {
            List<ReserveInfo> list = reserveInfoMapper.selectByExample(example);
            for(ReserveInfo reserveInfo : list) {
                reserveInfoMapper.deleteByPrimaryKey(reserveInfo.getInfoId());
            }
        } catch (DataAccessException e) {

        }
    }
}
