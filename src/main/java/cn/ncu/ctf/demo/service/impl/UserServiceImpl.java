package cn.ncu.ctf.demo.service.impl;

import cn.ncu.ctf.demo.entities.User;
import cn.ncu.ctf.demo.mapper.UserMapper;
import cn.ncu.ctf.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: jinge
 * @Date: 2022/10/25 21:38
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
