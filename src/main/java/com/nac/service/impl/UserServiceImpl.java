package com.nac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nac.dao.UserDao;
import com.nac.domain.User;
import com.nac.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public String selectUsername(User user) {
        return null;
    }

    @Override
    public String addUser(User user) {
      return null;
    }

}
