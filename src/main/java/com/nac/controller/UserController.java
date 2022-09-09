package com.nac.controller;

import com.nac.controller.utils.R;
import com.nac.dao.UserDao;
import com.nac.domain.User;
import com.nac.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private UserDao userDao;

    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public R register(@RequestBody User user) {
        String username= user.getUsername();
        String password= user.getPassword();
        if (userDao.selectUsername(username) == null){
            userService.addUser(user);
            return new R(true,null,"注册成功");
        }else {
            return new R(false,null,"注册失败，用户名已存在");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(@RequestBody User user) {
        String username= user.getUsername();
        String password=user.getPassword();
        System.out.println(username);
        System.out.println(password);
        if (userDao.selectUsername(username)==null){
            return new R(false,null,"用户不存在!");
        }else if (!userDao.selectPassword(username).equals(password)){
            return new R(false,null,"用户名或密码错误！");
        }else if (userDao.selectPassword(username).equals(password)){
            return new R(true,username,"登录成功！");
        }
        return new R(false,444,"系统出错！");
    }

    @ResponseBody
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public R select(@RequestBody User user) {

        String username = user.getUsername();
        System.out.println(user.getUsername());
        // 用户存在
        if(userDao.selectUsername(username) == null){
            String password = user.getPassword();
            System.out.println(username + "***" + password);
            userDao.addUser(username, password);
            return new R(true,null,"注册成功");
        }else {
            return new R(false,null,"注册失败");
        }
    }


}
