package com.nac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nac.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao extends BaseMapper<User> {

    @Select("select username from user where username = #{username}")
    public String selectUsername(String username);

    @Select("select password from user where username = #{username}")
    public String selectPassword(String username);

    @Insert("insert into user(username, password) values(#{username}, #{password})")
    public void addUser(String username, String password);

}
