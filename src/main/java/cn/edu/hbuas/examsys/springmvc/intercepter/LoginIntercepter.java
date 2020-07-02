package cn.edu.hbuas.examsys.springmvc.intercepter;

import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 牛传喜
 * 用户登录拦截器
 */
@Component
public class LoginIntercepter implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(LoginIntercepter.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        User user =(User) httpSession.getAttribute("user");
        /*if(null == user){
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));
            logger.info("LoginIntercepter：用户未登录");
            return false;
            //throw new BusinessException("用户未登录");
        }*/
        return true;
    }
}
