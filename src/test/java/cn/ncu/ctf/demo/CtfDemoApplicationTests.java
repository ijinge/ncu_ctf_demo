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
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        System.out.println(userList);
    }

}
