package cn.ncu.ctf.demo.fliter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  @author: ijnge
 *  @Date: 2022/10/30
 *  @Description: 后台保护
 */

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/admin/*")
public class LoginCheckFilter implements Filter {
    //进行路径比较 对于 /backend/index.html这样的进行过滤
    public static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //获取本次请求URI
        String requestURI = request.getRequestURI();
        //不拦截.只有这些才不拦截
        String[] uris = new String[]{
                "/admin/login",
                "/admin",
                "/admin/static/**",
        };
        //判断本次请求是否需要处理
        boolean check = check(uris, requestURI);

        log.info(request.getSession().toString());
        //不需要处理
        if (check) {
            filterChain.doFilter(request,response);
            log.info("放行请求：{}",request.getRequestURI());
            return;
        }
        //判断登录情况，如果登录了，就放行

        if (request.getSession().getAttribute("manager") != null) {
            log.info("放行请求：{}",request.getRequestURI());
            filterChain.doFilter(request,response);
            return;
        }

        //如果未登录成功，通过输出流的方式向客户端响应数据
        log.info("拦截请求：{}",request.getRequestURI());
        response.sendRedirect("/admin");
//        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * */
    public boolean check(String[] uris,String requestURI) {

        for(String uri : uris){
            //将请求URI 与 放行路径中的URI进行比较
            boolean match = ANT_PATH_MATCHER.match(uri, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}