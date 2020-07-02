package cn.edu.hbuas.examsys.service.baseinfo;

import cn.edu.hbuas.examsys.mybatis.mapper.RoomMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Room;
import cn.edu.hbuas.examsys.mybatis.pojo.RoomExample;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 牛传喜
 */

/**
 * 考场信息
 */
@Service
public class RoomService {

    @Autowired
    private RoomMapper roomMapper;


    /**
     * 添加考场信息
     * @param room
     */
    public void addTestRoom(Room room) throws Exception
    {
        RoomExample roomExample = new RoomExample();
        RoomExample.Criteria criteria = roomExample.createCriteria();
        criteria.andNumberEqualTo(room.getNumber());



        roomExample.or(criteria);
        List<Room> rooms = roomMapper.selectByExample(roomExample);
        if(rooms.size() > 0){

            throw new BusinessException("考场信息已经存在");
        }
        roomMapper.insertSelective(room);

    }


    /**
     * 根据主键删除考场信息
     * @param room
     * @throws Exception
     */
    public void deleteTestRoom(Room room) throws  Exception
    {
        if(roomMapper.selectByPrimaryKey(room.getRid())==null)
        {
            throw new BusinessException("不存在该考场!");
        }
        roomMapper.deleteByPrimaryKey(room.getRid());
    }

    /**
     * 分页查询所有考场信息
     *
     */
    public PageInfo<Room> selectAllRoom(Integer pageNum,Integer pageSize) throws Exception
    {
        if(null==pageNum || pageNum<0)
            pageNum=1;
        PageHelper.startPage(pageNum,pageSize);
        List<Room> rooms=roomMapper.selectByExample(null);
        if (rooms.size()==0)
        {
            throw new BusinessException("没有任何考场信息...");
        }
        PageInfo<Room> pageInfo=new PageInfo<>(rooms);
        return pageInfo;
    }

    /**
     * 修改考场信息
     */
    public void updateRoomInfo(Room room) throws Exception
    {
        if(roomMapper.selectByPrimaryKey(room.getRid())==null)
        {
            throw new BusinessException("不存在该考场");
        }
        roomMapper.updateByPrimaryKeySelective(room);
    }

    /**
     * 根据编号查询考场信息
     */
    public Room selectRoomById(Room room) throws Exception
    {
        Room room1=roomMapper.selectByPrimaryKey(room.getRid());
        if(room1==null)
        {
            throw new BusinessException("不存在该考场");
        }
        return room1;
    }
}
