package cn.edu.hbuas.examsys.controller;

import cn.edu.hbuas.examsys.dao.ArrangeDao;
import cn.edu.hbuas.examsys.model.Examinee;
import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.mybatis.mapper.ExamPlanMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Invigilation;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import cn.edu.hbuas.examsys.service.examplan.ArrangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@Api(value = "首页测试",tags = "首页测试")
public class Index {
    @Autowired
    private ArrangeDao arrangeDao;

    @Autowired
    private ExamPlanMapper examPlanMapper;

    @ApiOperation(value = "测试")
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    @ResponseBody
    public String index(HttpSession session) throws Exception{
        String a=session.getId();
        Invigilation invigilation = new Invigilation();
        invigilation.setTid(1);
        invigilation.setCid(2);
        List<Teacher> teachers = examPlanMapper.selectInviTeacher(invigilation);
        for (Teacher teacher:teachers){
            System.out.println(teacher.getTname());
        }
        return "首页访问成功 id="+a;
    }
}
