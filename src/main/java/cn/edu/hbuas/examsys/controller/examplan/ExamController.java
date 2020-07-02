package cn.edu.hbuas.examsys.controller.examplan;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Examcourse;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.service.examplan.ArrangeService;
import cn.edu.hbuas.examsys.service.examplan.ExamCourseService;
import cn.edu.hbuas.examsys.service.examplan.TaskPlanService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author SFX
 * 对考试科目的管理，
 */

@Api(tags = "考试科目管理，对考试科目进行增删改查")
@RestController
@RequestMapping("/examplan")
public class ExamController {
    private static Logger logger = LoggerFactory.getLogger(ExamController.class);
    @Autowired
    private TaskPlanService taskPlanService;
    @Autowired
    private ArrangeService arrangeService;
    @Autowired
    private ExamCourseService examCourseService;

    @GetMapping("/examCourses")
    @ApiOperation("获取一个考试任务下的全部考试科目")
    @ResponseBody
    public ResponseData getAllExamCourse(Integer pageNum,Integer pageSize,Integer tid, HttpSession httpSession) throws Exception{
        User user = (User) httpSession.getAttribute("user");
        Taskplan taskplan = taskPlanService.selectTaskPlanByTid(tid);
        if(null == taskplan){
            return new ResponseData("参数错误",false);
        }
        httpSession.setAttribute("taskplan",taskplan);
        Examcourse condition = new Examcourse();
        condition.setTid(tid);
        PageInfo<Examcourse> examcoursePageInfo = examCourseService.selectExamcourseByPage(user.getCid(),pageNum,pageSize,condition);
        ResponseData responseData = new ResponseData("数据获取成功",examcoursePageInfo.getTotal(),examcoursePageInfo.getList());
        return responseData;
    }

    @ApiOperation("二级学院添加一个考试科目")
    @PostMapping("/edit/examCourse")
    @ResponseBody
    public ResponseData insertExamCourse(Examcourse examcourse,HttpSession httpSession) throws Exception{
        Taskplan taskplan =(Taskplan) httpSession.getAttribute("taskplan");
        User user = (User) httpSession.getAttribute("user");
        examcourse.setTid(taskplan.getTid());
        examcourse.setCid(user.getCid());
        String courseName = examcourse.getName();
        if(courseName.contains("（")||courseName.contains("）")||courseName.contains(" "))
            throw new BusinessException("考试课程名称包含空格或中文括号:"+courseName);
        examCourseService.addExanCourse(examcourse);
        ResponseData responseData = new ResponseData("添加考试科目成功",true);
        responseData.setData(responseData);
        return responseData;
    }

    @ApiOperation("批量删除考试科目，单个删除或者多个删除")
    @DeleteMapping("/edit/examCourseList")
    @ResponseBody
    public ResponseData deleteExamCourse(@RequestBody List<Examcourse> deletes,HttpSession httpSession){
        Taskplan taskplan =(Taskplan) httpSession.getAttribute("taskplan");
        User user = (User) httpSession.getAttribute("user");
        examCourseService.deleteExamCourseList(deletes,user.getCid(),taskplan.getTid());
        ResponseData responseData = new ResponseData("删除数据成功",true);
        return responseData;
    }
}
