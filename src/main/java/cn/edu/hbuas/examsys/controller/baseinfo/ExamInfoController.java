package cn.edu.hbuas.examsys.controller.baseinfo;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.mybatis.pojo.Surface;
import cn.edu.hbuas.examsys.service.baseinfo.ExamInfoService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 牛传喜
 */

@Api(value = "基础数据-考试信息管理")
@RestController
@RequestMapping(value = "/dept")
public class ExamInfoController {
    @Autowired
    private ExamInfoService examInfoService;

    @ApiOperation(value = "添加考试信息")
    @PostMapping(value = "/surface")
    @ResponseBody
    public ResponseData addExamIndo(Surface surface) throws Exception{
        if(null == surface.getTid()){
            throw new BusinessException("参数错误");
        }
        examInfoService.addData(surface);
        return new ResponseData("添加考试数据成功",true);
    }

    @ApiOperation(value = "删除考试信息")
    @DeleteMapping(value = "/surface")
    @ResponseBody
    public ResponseData deleteExamIndo(Surface surface) throws Exception{
        if(null == surface.getTid()){
            throw new BusinessException("参数错误");
        }
        examInfoService.deleteDataByID(surface);
        return new ResponseData("删除考试数据成功",true);
    }

    @ApiOperation(value = "修改考试信息")
    @PutMapping(value = "/surface")
    @ResponseBody
    public ResponseData editExamIndo(Surface surface) throws Exception{
        if(null == surface.getTid()){
            throw new BusinessException("参数错误");
        }
        examInfoService.updateDataByID(surface);
        return new ResponseData("修改考试数据成功",true);
    }

    @ApiOperation(value = "查询全部考试信息")
    @GetMapping(value = "/surfaces")
    @ResponseBody
    public ResponseData getAllExamIndo() throws Exception{
        ResponseData responseData = new ResponseData("查询考试数据成功",true);
        responseData.setData(examInfoService.getAllData());
        return responseData;
    }
}
