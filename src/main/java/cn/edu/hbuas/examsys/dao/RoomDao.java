package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.RoomMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Room;
import cn.edu.hbuas.examsys.mybatis.pojo.RoomExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SFX
 * 教室表的操作：room
 */

@Repository
public class RoomDao {
    @Autowired
    private RoomMapper roomMapper;

    /**
     * 查询所有的教室
     * @return
     */
    public List<Room> selectAllRoom(){
        RoomExample roomExample = new RoomExample();
        RoomExample.Criteria criteria = roomExample.createCriteria();
        roomExample.setOrderByClause("campus,storid,number");
        return roomMapper.selectByExample(roomExample);
    }

    /**
     * 根据条件删除教室映射，如果条件属性为空，则不参与选择
     * @param condition
     * @return
     */
    public int deleteRoomByCondition(Room condition){
        RoomExample roomExample = new RoomExample();
        RoomExample.Criteria criteria = roomExample.createCriteria();
        if(null != condition.getTid())
            criteria.andTidEqualTo(condition.getTid());
        if(null != condition.getRid())
            criteria.andRidEqualTo(condition.getRid());
        if(null != condition.getPlace())
            criteria.andPlaceEqualTo(condition.getPlace());
        if(null != condition.getNumber())
            criteria.andNumberEqualTo(condition.getNumber());
        return roomMapper.deleteByExample(roomExample);
    }


    /**
     * 多重条件查询考场教室
     * @param condition
     * @return
     */
    public List<Room> selectRoomByCondition(Room condition){
        RoomExample roomExample = new RoomExample();
        RoomExample.Criteria criteria = roomExample.createCriteria();
        if(null != condition.getTid())
            criteria.andTidEqualTo(condition.getTid());
        if(null != condition.getRid())
            criteria.andRidEqualTo(condition.getRid());
        if(null != condition.getPlace())
            criteria.andPlaceEqualTo(condition.getPlace());
        if(null != condition.getNumber())
            criteria.andNumberEqualTo(condition.getNumber());
        if(null != condition.getCampus())
            criteria.andCampusEqualTo(condition.getCampus());
        if(null != condition.getStorid())
            criteria.andStoridEqualTo(condition.getStorid());
        return roomMapper.selectByExample(roomExample);
    }
}
