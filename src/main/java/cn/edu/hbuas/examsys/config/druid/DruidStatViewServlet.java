package cn.edu.hbuas.examsys.config.druid;


import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.Servlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(
        urlPatterns= {"/druid/*"},
        initParams= {
                @WebInitParam(name="allow",value="127.0.0.1"),
                @WebInitParam(name="loginUsername",value="root"),
                @WebInitParam(name="loginPassword",value="123"),
                @WebInitParam(name="resetEnable",value="true")
        }
)
public class DruidStatViewServlet extends StatViewServlet implements Servlet {
    private static final long serialVersionUID = 1L;
}
