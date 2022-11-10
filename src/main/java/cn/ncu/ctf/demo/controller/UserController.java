package cn.ncu.ctf.demo.controller;

import cn.ncu.ctf.demo.entities.Manager;
import cn.ncu.ctf.demo.entities.User;
import cn.ncu.ctf.demo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author: jinge
 * @Date: 2022/10/26 23:05
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;



    @RequestMapping({"/","index"})
    public String index(HttpServletRequest httpServletRequest, Model model) {
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        model.addAttribute("user",user);
        return "index";
    }


    /**
     *  @author: ijnge
     *  @Date: 2022/11/10
     *  @Description:登录页
     */
    @RequestMapping("/login")
    public String LoginPage(@ModelAttribute("message") String message, Model model) {
        model.addAttribute("message",message);
        return "Login";
    }

    @RequestMapping("/user/login")
    public String userLogin(
            User user,
            HttpServletRequest httpServletRequest,
            RedirectAttributes redirectAttributes,
            Model model) {
        //将页面提交的密码md5加密
        log.info("user: "+user);
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());

        //根据username查询用户user
        User userServiceOne = userService.getOne(queryWrapper);

        if (userServiceOne == null) {
            log.info("未查询到用户");
            redirectAttributes.addFlashAttribute("message","未查询到用户"+user.getUsername());
            return "redirect:/login";
        }
        //密码比对
        if(!userServiceOne.getPassword().equals(password)){
            log.info("密码错误");
            redirectAttributes.addFlashAttribute("message","密码错误");

            return  "redirect:/login";
        }
        //用户登录成功 将id存储session
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("user",userServiceOne);
        model.addAttribute("user",userServiceOne);

        return "/index";
    }


    @RequestMapping("/register")
    public String RegisterPage(
            @ModelAttribute("message") String message, Model model
    ) {
        model.addAttribute("message",message);
        return "Register";
    }

    @RequestMapping("/user/register")
    public String UserRegister(
            User user,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        //根据username查询用户user
        User readyUser = userService.getOne(queryWrapper);

        //用户已经注册
        if(readyUser != null) {
            log.info("用户已经存在");
            redirectAttributes.addFlashAttribute("message","用户"+user.getUsername()+"已存在，请返回登录页登录");
            return "redirect:/register";
        }
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(password);
        userService.save(user);
        model.addAttribute("user",user);

        return "Login";
    }

    @RequestMapping("/challenges")
    public String challenge() {
        return "challenge";
    }

    @RequestMapping("/solutions")
    public String solution() {
        return "solutions";
    }





}
