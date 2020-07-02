package cn.edu.hbuas.examsys.controller.examplan;

import cn.edu.hbuas.examsys.model.Examinee;
import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Arrange;
import cn.edu.hbuas.examsys.mybatis.pojo.Examcourse;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.service.examplan.ArrangeService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.utils.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

/**
 * @author SFX
 * 考生数据管理
 */
@RestController
@Api(tags = "手动添加考生和重修学生")
@RequestMapping("/examplan")
public class ArrangeController {
    @Autowired
    private ArrangeService arrangeService;

    @ApiOperation("添加一个重修考试学生")
    @PostMapping("/edit/addreststu")
    @ResponseBody
    public ResponseData addRestArrange(Arrange arrange, HttpSession session) throws Exception{
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan");
        User user = (User) session.getAttribute("user");
        arrange.setTid(taskplan.getTid());
        arrangeService.insertArrangeStudent(arrange);
        return new ResponseData("添加重修考试学生成功",true);
    }

    @ResponseBody
    @PostMapping("/edit/delrestarrs")
    @ApiOperation("批量删除考试学生")
    public ResponseData deleteRestStuList(@RequestBody List<Arrange> arrangeList, HttpSession session) throws Exception{
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan");
        User user = (User) session.getAttribute("user");
        if(arrangeList.size() <= 0)
            throw new BusinessException("参数错误");
        arrangeService.delRestArrList(arrangeList,taskplan);
        return new ResponseData("删除考生成功",true);
    }


    @ApiOperation(value = "批量上传重修学生信息")
    @RequestMapping(value = "/edit/rsetlist", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData readReset(MultipartFile file, HttpSession session, Integer eid) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan");
        List<String> propertys= Arrays.asList("sid","aname");
        List<Arrange> arrangeList= FileUtil.readCommonalityExcel(file,0,propertys,new Arrange());
        arrangeService.addRestArrList(arrangeList,taskplan,eid,1);
        return new ResponseData("添加成功", true);
    }

    @ResponseBody
    @ApiOperation("获取一个考试科目下的全部重修学生")
    @PostMapping("/edit/getrestarrlist")
    public ResponseData getRestArrList(Integer eid,Integer pageNum,Integer pageSize,HttpSession session) throws Exception{
        if(pageNum == null || pageSize == null)
            throw new BusinessException("参数错误");
        if(pageNum < 0 || pageSize < 0)
            throw new BusinessException("参数错误");
        PageHelper.startPage(pageNum,pageSize);
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan");
        PageInfo<Examinee> examineePageInfo =  new PageInfo<>(arrangeService.getRestArrList(eid,taskplan.getTid(),1));
        ResponseData responseData = new ResponseData("查询成功",true);
        responseData.setRows(examineePageInfo.getList());
        responseData.setTotal(examineePageInfo.getTotal());
        return responseData;
    }

    @ResponseBody
    @ApiOperation("获取一个考试科目下的全部正常考试学生")
    @PostMapping("/edit/getCommArrlist")
    public ResponseData getCommArrList(Integer eid,Integer pageNum,Integer pageSize,HttpSession session) throws Exception{
        if(pageNum == null || pageSize == null)
            throw new BusinessException("参数错误");
        if(pageNum < 0 || pageSize < 0)
            throw new BusinessException("参数错误");
        PageHelper.startPage(pageNum,pageSize);
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan");
        PageInfo<Examinee> examineePageInfo =  new PageInfo<>(arrangeService.getRestArrList(eid,taskplan.getTid(),0));
        ResponseData responseData = new ResponseData("查询成功",true);
        responseData.setRows(examineePageInfo.getList());
        responseData.setTotal(examineePageInfo.getTotal());
        return responseData;
    }


}
