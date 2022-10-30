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
            //重定向
            return "redirect:/admin";
        }
        //密码比对
        if(!ManagerByName.getPassword().equals(password)){
            log.info("密码错误");
            redirectAttributes.addFlashAttribute("message","密码错误");
            //重定向
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
     *  @Date: 2022/10/31
     *  @Description:返回用户列表未做分页
     */

    @RequestMapping("/admin/UserManage")
    public String UserList(Model model) {
        List<User> list = userService.list();
        model.addAttribute("userList",list);
        return "userList";
    }

}
