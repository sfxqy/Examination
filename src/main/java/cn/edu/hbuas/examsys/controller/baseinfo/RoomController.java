
package cn.edu.hbuas.examsys.controller.baseinfo;

import cn.edu.hbuas.examsys.model.ResponseData;
import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.pojo.Room;
import cn.edu.hbuas.examsys.service.baseinfo.RoomService;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author 牛传喜
 */

@Api(tags = "基础数据-考场信息管理")
@RequestMapping("/dept")
@RestController
@CrossOrigin
public class RoomController {

    @Autowired
    private RoomService roomService;



    @ResponseBody
    @ApiOperation("添加考场信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid",value = "考场编号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "place",value = "考场地点",required = true,dataType = "String"),
            @ApiImplicitParam(name = "number",value = "教室号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "campus ",value = "校区",required = true,dataType = "int"),
            @ApiImplicitParam(name = "storid",value = "楼栋",required = true,dataType = "int"),
            @ApiImplicitParam(name = "capacity",value = "教室容量",required = true,dataType = "int")
    })
    @PostMapping("/room")
    public ResponseData addTestRoom(Room room,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        if(room.getPlace()==null )
            throw new BusinessException("数据非法");
        roomService.addTestRoom(room);
        ResponseData   responseData = new ResponseData("添加考场信息成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation("删除考场信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid",value = "考场编号",required = true,dataType = "int"),
    })
    @DeleteMapping("/room")
    public ResponseData deleteRoom(Room room, HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        if(room.getRid()==null)
        {
           throw new BusinessException("提交的数据错误");
        }

        roomService.deleteTestRoom(room);
        ResponseData responseData=new ResponseData("删除考场信息成功",true);
        return responseData;
    }

    @ResponseBody
    @ApiOperation("修改考场信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid",value = "考场编号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "place",value = "考场地点",required = true,dataType = "String"),
            @ApiImplicitParam(name = "number",value = "教室号",required = true,dataType = "int"),
            @ApiImplicitParam(name = "campus ",value = "校区",required = true,dataType = "int"),
            @ApiImplicitParam(name = "storid",value = "楼栋",required = true,dataType = "int"),
            @ApiImplicitParam(name = "capacity",value = "教室容量",required = true,dataType = "int")
    })
    @PutMapping("room")
    public ResponseData updateRoom(Room room,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        if(room==null)
        {
            throw new BusinessException("未做修改");
        }
        roomService.updateRoomInfo(room);
        ResponseData responseData=new ResponseData("修改考场信息成功",true);
        return  responseData;
    }

    @ResponseBody
    @ApiOperation("分页查询所有考场信息")
    @GetMapping("rooms")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, dataType = "int")
    })
    public HashMap<String,Object> selectRooms(Integer pageNum, Integer pageSize,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        HashMap<String,Object> hashMap=new HashMap<>();
        PageInfo<Room> pageInfo=roomService.selectAllRoom(pageNum,pageSize);
        hashMap.put("offset",1);
        hashMap.put("total",pageInfo.getTotal());
        hashMap.put("rows",pageInfo.getList());
        return  hashMap;
    }

    @ResponseBody
    @ApiOperation("根据id查询考场信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid",value = "考场编号",required = true,dataType = "int"),
    })
    @GetMapping("/room")
    public ResponseData selectRoomById(Room room,HttpSession session) throws Exception
    {
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=-1)
        {
            throw new BusinessException("您的权限不足！");
        }
        if(room.getRid()==null)
        {
            throw new BusinessException("请输入要查询的考场编号");
        }
        ResponseData responseData=new ResponseData("查询考场信息成功",true);
        responseData.setData(roomService.selectRoomById(room));
        return  responseData;
    }
}

