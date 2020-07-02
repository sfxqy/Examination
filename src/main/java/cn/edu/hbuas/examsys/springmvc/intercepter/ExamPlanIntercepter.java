package cn.edu.hbuas.examsys.springmvc.intercepter;

import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 牛传喜
 */
@Component
public class ExamPlanIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        Taskplan taskplan = (Taskplan) httpSession.getAttribute("taskplan");
        User user = (User)httpSession.getAttribute("user");
        Integer state = Integer.parseInt(taskplan.getState());
        if((user.getCid() < 0)||(state < 2))
            return true;
        else if(state >1){
            throw new BusinessException("数据不可更改");
        }
        return false;
    }
}
