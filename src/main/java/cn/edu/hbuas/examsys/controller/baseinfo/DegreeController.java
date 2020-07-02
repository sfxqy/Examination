package cn.edu.hbuas.examsys.controller.baseinfo;


import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.service.examplan.DegreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 牛传喜
 */
@RestController
@Api(tags = "学位考试相关")
@RequestMapping("/degree")
public class DegreeController {

    @Autowired
    DegreeService degreeService;

    /**
     * @param file
     * @param session
     * @return
     */
    @PostMapping("/uploadDegreeInfo")
    @ApiOperation(value = "上传学位考试的学生信息")
    public ResponseData uploadDegreeInfo(MultipartFile file, HttpSession session) throws Exception {
        Taskplan taskplan = (Taskplan)session.getAttribute("taskplan") ;
        Integer tid=taskplan.getTid();
        degreeService.uploadDegreeInfo(tid,file,session);
        return new ResponseData("学位考试学生信息已上传",true);
    }


    @GetMapping("/getDegreeAdCards")
    @ApiOperation(value = "获取学位准考证")
    public void getCards(HttpServletResponse response,HttpSession session){
        degreeService.getDegreeAdmissionCards(session,response,4);
    }

}
