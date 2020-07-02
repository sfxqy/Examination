package cn.edu.hbuas.examsys.springmvc;

import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.springmvc.exception.CheckException;
import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.springmvc.exception.FileException;
import cn.edu.hbuas.examsys.springmvc.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author SFX
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandle {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandle.class);
    /**
     * 验证类异常，空
     * @param exception
     * @return
     */
    @ExceptionHandler(CheckException.class)
    @org.springframework.web.bind.annotation.ResponseBody
    public ResponseData customGenericExceptionHnadler(CheckException exception) {
        return new ResponseData(exception.getLocalizedMessage(),false);
    }

    /**
     * 业务处理异常
     * @param exception
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseData businessExceptionExceptionHnadler(BusinessException exception) {
        return new ResponseData(exception.getLocalizedMessage(),false);
    }

    /**
     * 处理持久层事务回滚异常
     * @param exception
     * @return
     */
    @ExceptionHandler(RepositoryException.class)
    @ResponseBody
    public ResponseData repositoryExceptionHandler(RepositoryException exception){
        if(null != exception.getRepMsg())
        logger.error(exception.getRepMsg());
        return new ResponseData(exception.getLocalizedMessage(),false);
    }

    /**
     * 文件处理异常
     * @param exception
     * @return
     */
    @ExceptionHandler(FileException.class)
    @ResponseBody
    public ResponseData fileExceptionExceptionHnadler(FileException exception) {
        return new ResponseData(exception.getLocalizedMessage(),false);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseData exceptionHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        ResponseData responseData = new ResponseData(e.getLocalizedMessage(),false);
        return responseData;
    }
}
