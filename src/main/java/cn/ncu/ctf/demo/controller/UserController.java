package cn.ncu.ctf.demo.controller;

import cn.ncu.ctf.demo.common.R;
import cn.ncu.ctf.demo.entities.Manager;
import cn.ncu.ctf.demo.entities.User;
import cn.ncu.ctf.demo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  @author: ijnge
     *  @Date: 2022/10/26
     *  @Description: 用户登录判断
     */
    @PostMapping("/user/login")
    public R<User> login(
            @RequestBody User user,
            HttpServletRequest request)
    {
        //将页面提交的密码md5加密
        String password = user.getPassword();

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());

        //根据username查询用户user
        User getUserByName = userService.getOne(queryWrapper);

        if (getUserByName == null) {
            return R.error("未查询到用户"+user.getUsername());
        }
        //密码比对
        if(!getUserByName.getPassword().equals(password)){
            return R.error("密码错误");
        }
        //用户登录成功 将id存储session
        request.getSession().setAttribute("user",user.getId_user());
        return R.success(getUserByName);
    }

    /**
     *  @author: ijnge
     *  @Date: 2022/10/26
     *  @Description: 添加用户
     */
    @PostMapping("/user/add")
    public R<String> addUser(@RequestBody User user) {
        //加密后的password
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());

        user.setPassword(password);
        user.setScores(0L);
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
        return R.success("用户"+user.getUsername()+"添加成功!");
    }

    /**
     *  @author: ijnge
     *  @Date: 2022/10/26
     *  @Description: 删除用户
     */
    @PostMapping("/user/delete")
    public R<String> deleteUserById(@RequestBody User user, HttpSession httpSession) {
        Manager admin = (Manager) httpSession.getAttribute("user");
        //只有超级管理员和用户管理员才可以删除
        if (admin.getLevel() == 1 || admin.getLevel() == 2) {
            userService.removeById(user.getId_user(),true);
            return R.success("删除用户"+user.getUsername()+"成功");
        }
        else return R.error("管理员"+admin.getUsername()+"无权限");
    }








}
