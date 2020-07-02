package cn.edu.hbuas.examsys.controller.editinfo;


import cn.edu.hbuas.examsys.dao.SurfaceDao;
import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Surface;
import cn.edu.hbuas.examsys.mybatis.pojo.SurfaceDTO;
import cn.edu.hbuas.examsys.mybatis.pojo.Taskplan;
import cn.edu.hbuas.examsys.service.editinfo.EditService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @author 牛传喜
 */
@Api(tags = "监考教师安排信息")
@RestController
public class SurfaceController {

    @Autowired
    private EditService editService;

    /**
     * @param httpSession
     * @param pageNum     第几页
     * @param pageSize    条数
     * @return
     */
    @ApiOperation(value = "分页获取本院的监考安排信息")
    @GetMapping("/getSurfaceInfoByPage")
    public HashMap<String, Object> getSurfaceInfoByPage(HttpSession httpSession,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User u = (User) httpSession.getAttribute("user");
        Taskplan taskplan = (Taskplan) httpSession.getAttribute("taskplan");
        int tid = taskplan.getTid();
        int cid = u.getCid();
        List<SurfaceDTO> surfaceList = editService.getSurfaceInfoByCid(tid, cid, pageNum, pageSize);
        HashMap<String, Object> hashMap = new HashMap<>(16);
        hashMap.put("offset", 1);
        hashMap.put("total", surfaceList.size());
        hashMap.put("rows", surfaceList);
        return hashMap;
    }


    @ApiOperation(value = "修改监考教师安排")
    @PostMapping("/updateSurfaceInfo")
    public ResponseData updateSurfaceInfo(SurfaceDTO surfaceDTO, HttpSession session) {
//        User user = (User) session.getAttribute("user");
        int row = -1;
        try {
//            row = editService.updateSurfaceInfo(surfaceDTO,user.getCid());
            row = editService.updateSurfaceInfo(surfaceDTO);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseData("修改监考教师信息出错", false);
        }
        if (row == 0) {
            return new ResponseData("该监考教师在该时间段已有安排监考，不能添加", false);
        }
        return new ResponseData("修改成功", true);

    }

    /**
     * 根据传入的监考教师姓名和科目查询相应的安排情况
     *
     * @param teacherName
     * @param subjectName
     * @return
     */
    @ApiOperation(value = "根据条件查询对应的监考信息")
    @GetMapping("/filterSurface")
    public HashMap<String, Object> getSurfaceByNameAndSubject(HttpSession httpSession, String teacherName,
                                                              String subjectName) {
        HashMap<String, Object> hashMap = new HashMap<>(16);
        if (StringUtils.isEmpty(teacherName) && StringUtils.isEmpty(subjectName)) {
            hashMap.put("msg", "请传入有效数据");
            return hashMap;
        }
        User user = (User) httpSession.getAttribute("user");
        Taskplan taskplan = (Taskplan) httpSession.getAttribute("taskplan");

        List<SurfaceDTO> surfaceList = editService.getSurfaceByTeacherNameAndSubject(user.getCid(), taskplan.getTid()
                , teacherName,
                subjectName);
        hashMap.put("offset", 1);
        hashMap.put("total", surfaceList.size());
        hashMap.put("rows", surfaceList);
        return hashMap;
    }


}
