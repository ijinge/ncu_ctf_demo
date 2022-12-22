package cn.ncu.ctf.demo.filter;

import cn.ncu.ctf.demo.entities.Manager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author: jinge
 * @Date: 2022/11/13 20:31
 */

@Slf4j
@WebFilter(filterName = "PermissionsFilter",urlPatterns = {"/admin/managerList","/admin/UserManage/**"})
public class PermissionsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        HttpSession session = request.getSession();
        Manager manager = (Manager) session.getAttribute("manager");
        log.info("拦截请求：{}",request.getRequestURI());
        //权限不够
        log.info("当前用户权限等级 {}",manager.getLevel());
        if(manager.getLevel() > 2) response.sendRedirect("/PermissionError");
        else filterChain.doFilter(request,response);
    }

}
