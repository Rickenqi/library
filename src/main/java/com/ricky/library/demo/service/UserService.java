package com.ricky.library.demo.service;

import com.ricky.library.demo.domain.UserInfo;
import com.ricky.library.demo.mapper.UserInfoMapper;
import com.ricky.library.demo.util.result.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(username);
        LoginUser loginUser = new LoginUser();
        if (userInfo == null) {
            throw new UsernameNotFoundException("数据库中无此用户！");
        }
        loginUser.setUsername(userInfo.getUserId());
        loginUser.setUsername(userInfo.getUserId());
        loginUser.setRole(userInfo.getUserRole());
        return loginUser;
    }
}
