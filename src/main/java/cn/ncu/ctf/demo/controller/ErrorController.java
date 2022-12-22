package cn.ncu.ctf.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: jinge
 * @Date: 2022/11/13 20:40
 */

@Controller
public class ErrorController {


    @RequestMapping("/PermissionError")
    public String PermissionError() {
        return "/error/PermissionError";
    }
}
