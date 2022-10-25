package cn.ncu.ctf.demo.controller;

import cn.ncu.ctf.demo.common.R;
import cn.ncu.ctf.demo.entities.User;
import cn.ncu.ctf.demo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.sun.webkit.perf.WCFontPerfLogger.log;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public R<User> login(
            User user,
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
        request.getSession().setAttribute("employee",user.getId_user());
        return R.success(getUserByName);
    }

}
