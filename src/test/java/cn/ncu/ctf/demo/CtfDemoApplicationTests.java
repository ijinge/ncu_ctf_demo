package cn.ncu.ctf.demo;

import cn.ncu.ctf.demo.entities.User;
import cn.ncu.ctf.demo.mapper.UserMapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CtfDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("图图");
        user.setAddress("翻斗花园");
        user.setEmail("2312387123@qq.com");
        user.setPassword("123456789");
        user.setScores(100L);
        user.setPhone("1783218973");
        user.setHeadImage("null");

        userMapper.insert(user);
    }

}
