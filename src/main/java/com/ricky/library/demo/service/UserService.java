package com.ricky.library.demo.service;

import com.ricky.library.demo.domain.UserInfo;
import com.ricky.library.demo.mapper.UserInfoMapper;
import com.ricky.library.demo.util.result.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = new UserInfo();
        try {
            userInfo = userInfoMapper.selectByPrimaryKey(username);
        } catch(DataAccessException e) {
            throw new UsernameNotFoundException("数据库访问异常");
        }
        LoginUser loginUser = new LoginUser();
        if (userInfo == null) {
            throw new UsernameNotFoundException("数据库中无此用户！");
        }
        loginUser.setUsername(userInfo.getUserId());
        loginUser.setPassword(new BCryptPasswordEncoder().encode(userInfo.getUserPassword()));
        loginUser.setRole(userInfo.getUserRole());
        return loginUser;
    }
}
