package cn.ncu.ctf.demo.controller;

import cn.ncu.ctf.demo.common.R;
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
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
@Slf4j
@Controller
public class AdminController {

    @Autowired
    private ManagerService managerService;

    @Autowired
    private UserService userService;

    //跳转到后台登录页
    @RequestMapping({"/admin"})
    public String loginPage() {
        return "admin/login";
    }

    /**
     *  @author: ijnge
     *  @Date: 2022/10/26
     *  @Description: 用户登录判断
     */
    @RequestMapping({"/admin/login"})
    public String login(
            Manager manager,
            HttpServletRequest request,
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
            model.addAttribute("message","未查询到用户"+manager.getUsername());
            //重定向
            return "redirect:/admin";
        }
        //密码比对
        if(!ManagerByName.getPassword().equals(password)){
            log.info("密码错误");
            model.addAttribute("message","密码错误");
            //重定向
            return  "redirect:/admin";
        }
        //用户登录成功 将id存储session
        request.getSession().setAttribute("admin",manager.getId_manager());

        model.addAttribute("manager",ManagerByName);

        return "/admin/index";
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


    @PostMapping("/user/update")
    public R<String> updateUser(@RequestBody User user, HttpSession httpSession) {

        Manager admin = (Manager) httpSession.getAttribute("user");
        //只有超级管理员和用户管理员才可以修改
        if (admin.getLevel() == 1 || admin.getLevel() == 2) {
            userService.updateById(user);
            return R.success("用户"+user.getUsername()+"修改成功");
        }
        else return R.error("管理员"+admin.getUsername()+"无权限");
    }









}
