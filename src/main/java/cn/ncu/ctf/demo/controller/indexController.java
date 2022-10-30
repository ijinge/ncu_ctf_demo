package cn.ncu.ctf.demo.controller;

import cn.ncu.ctf.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: jinge
 * @Date: 2022/10/26 23:05
 */
@Controller
public class indexController {

    @Autowired
    private UserService userService;



    @RequestMapping({"/","index"})
    public String index() {
        return "index";
    }


    @RequestMapping("/login")
    public String userLogin() {
        return "index";
    }

    @RequestMapping("/challenge")
    public String challenge() {
        return "challenge";
    }




}
