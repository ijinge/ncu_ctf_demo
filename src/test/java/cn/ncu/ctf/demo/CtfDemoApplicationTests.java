package cn.ncu.ctf.demo;

import cn.ncu.ctf.demo.entities.Manager;
import cn.ncu.ctf.demo.entities.User;
import cn.ncu.ctf.demo.mapper.UserMapper;
import cn.ncu.ctf.demo.service.ManagerService;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CtfDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ManagerService managerService;
    @Test
    void contextLoads() {
        Manager user = new Manager();
        user.setUsername("ijinge");
        user.setEmail("2312387123@qq.com");
        user.setPassword("zxl19730926");
        user.setPhone("1783218973");
        user.setHeadImage("null");
        user.setLevel(1);

        managerService.save(user);
    }

}
