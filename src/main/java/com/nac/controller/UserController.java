package com.nac.controller;

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

    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@RequestBody User user) {
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user) {
        return userService.selectUsername(user);
    }

    @ResponseBody
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String select(@RequestBody User user) {
        return userService.selectUsername(user);
    }


}
