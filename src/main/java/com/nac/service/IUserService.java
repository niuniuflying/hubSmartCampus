package com.nac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nac.domain.User;

public interface IUserService extends IService<User> {


    String selectUsername(User user);
    String addUser(User user);

}
