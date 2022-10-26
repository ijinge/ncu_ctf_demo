package cn.ncu.ctf.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: jinge
 * @Date: 2022/10/26 23:05
 */
@Controller
public class indexController {


    @RequestMapping({"/","index"})
    public String index() {
        return "index";
    }

}
