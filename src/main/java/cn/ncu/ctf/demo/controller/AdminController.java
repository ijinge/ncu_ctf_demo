package cn.ncu.ctf.demo.controller;

import cn.ncu.ctf.demo.entities.Manager;
import cn.ncu.ctf.demo.entities.User;
import cn.ncu.ctf.demo.service.ManagerService;
import cn.ncu.ctf.demo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class AdminController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserService userService;

    //跳转到后台登录页
    @RequestMapping("/admin")
    public String loginPage(@ModelAttribute("message") String message, Model model) {
        model.addAttribute("message",message);
        return "admin/login";
    }

    /**
     *  @author: ijnge
     *  @Date: 2022/10/26
     *  @Description: 处理用户登录请求
     */
    @RequestMapping("/admin/login")
    public String login(
            Manager manager,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            Model model)
    {
        //将页面提交的密码md5加密
        String password = manager.getPassword();

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Manager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Manager::getUsername,manager.getUsername());

        //根据username查询用户user
        Manager ManagerByName = managerService.getOne(queryWrapper);

        if (ManagerByName == null) {
            log.info("未查询到用户");
            redirectAttributes.addFlashAttribute("message","未查询到用户"+manager.getUsername());

            return "redirect:/admin";
        }
        //密码比对
        if(!ManagerByName.getPassword().equals(password)){
            log.info("密码错误");
            redirectAttributes.addFlashAttribute("message","密码错误");

            return  "redirect:/admin";
        }
        //用户登录成功 将id存储session
        HttpSession session = request.getSession();
        session.setAttribute("manager",ManagerByName);

        model.addAttribute("manager",ManagerByName);
        return "/admin/index";
    }

    /**
     *  @author: ijnge
     *  @Date: 2022/11/9
     *  @Description: 登出
     */
    @RequestMapping("/admin/layout")
    public String layout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("manager");
        return "redirect:/admin";
    }

    /**
     *  @author: ijnge
     *  @Date: 2022/10/31
     *  @Description:返回用户列表未做分页
     */
    @RequestMapping("/admin/UserManage")
    public String UserList(Model model,HttpServletRequest httpServletRequest) {
        Manager manager =(Manager) httpServletRequest.getSession().getAttribute("manager");
        List<User> list = userService.list();
        model.addAttribute("userList",list);
        model.addAttribute("manager",manager);
        return "/admin/userList";
    }

    /**
     *  @author: ijnge
     *  @Date: 2022/11/8
     *  @Description: 返回管理员列表
     */
    @RequestMapping("/admin/managerList")
    public String ManagerList(Model model,HttpServletRequest httpServletRequest) {
        Manager manager =(Manager) httpServletRequest.getSession().getAttribute("manager");
        List<Manager> list = managerService.list();
        ArrayList<String> primary_levelList = new ArrayList<String>(){{
            add("超级管理员");
            add("用户管理员");
            add("题库管理员");
            add("社区管理员");
        }};

        ArrayList<String> levelList = new ArrayList<>();
        for(int i=0;i< list.size();i++) {
            levelList.add(i, primary_levelList.get(manager.getLevel()));
        }
        System.out.println(levelList);
        model.addAttribute("levelList",levelList);
        model.addAttribute("ManagerList",list);
        model.addAttribute("manager",manager);
        return "/admin/managerList";
    }



}
