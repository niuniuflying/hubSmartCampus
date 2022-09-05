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
        String username = user.getUsername();
        String password = user.getPassword();
        System.out.println(username);
        System.out.println(password);

        String result = "-1";

        // 将输入的密码使用md5加密
//        String passwordMD5 = passwordMD5(username, password);

        // 用户不存在
        if (userDao.selectUsername(username) == null) {
            result = "0";
            return result;
            //  用户存在，但密码输入错误
//        }else if(!userDao.selectPassword(username).equals(passwordMD5) ){
        }else if(!userDao.selectPassword(username).equals(password) ){
            result = "1";
            return result;
            //  登录成功
//        }else if(userDao.selectPassword(username).equals(passwordMD5)) {
        }else if(userDao.selectPassword(username).equals(password)) {
            result = "2";
            return result;
        }
        return result;
    }

    @Override
    public String addUser(User user) {

        String username = user.getUsername();
        // 用户存在
        if(userDao.selectUsername(username) != null){
            return "0";
        }else {
            String password = user.getPassword();
            System.out.println(username + "***" + password);
//        String passwordMD5 = passwordMD5(username, password);
//        userDao.addUser(username, passwordMD5);
            userDao.addUser(username, password);
            return "1";
        }




    }

    /*@Override
    public String passwordMD5(String username, String password) {
        // 需要加密的字符串
        String src = username + password;
        try {
            // 加密对象，指定加密方式
            MessageDigest md5 = MessageDigest.getInstance("md5");
            // 准备要加密的数据
            byte[] b = src.getBytes();
            // 加密：MD5加密一种被广泛使用的密码散列函数，
            // 可以产生出一个128位（16字节）的散列值（hash value），用于确保信息传输完整一致
            byte[] digest = md5.digest(b);
            // 十六进制的字符
            char[] chars = new char[]{'0', '1', '2', '3', '4', '5',
                    '6', '7', 'A', 'B', 'C', 'd', 'o', '*', '#', '/'};
            StringBuffer sb = new StringBuffer();
            // 处理成十六进制的字符串(通常)
            // 遍历加密后的密码，将每个元素向右位移4位，然后与15进行与运算(byte变成数字)
            for (byte bb : digest) {
                sb.append(chars[(bb >> 4) & 15]);
                sb.append(chars[bb & 15]);
            }
            // 打印加密后的字符串
            System.out.println(sb);
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }*/
}
