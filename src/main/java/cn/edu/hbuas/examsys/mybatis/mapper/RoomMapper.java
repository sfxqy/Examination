package cn.edu.hbuas.examsys.mybatis.mapper;


import cn.edu.hbuas.examsys.mybatis.pojo.Room;
import cn.edu.hbuas.examsys.mybatis.pojo.RoomExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoomMapper {
    int countByExample(RoomExample example);

    int deleteByExample(RoomExample example);

    int deleteByPrimaryKey(Integer rrid);

    int insert(Room record);

    int insertSelective(Room record);

    List<Room> selectByExample(RoomExample example);

    Room selectByPrimaryKey(Integer rrid);

    int updateByExampleSelective(@Param("record") Room record, @Param("example") RoomExample example);

    int updateByExample(@Param("record") Room record, @Param("example") RoomExample example);

    int updateByPrimaryKeySelective(Room record);

    int updateByPrimaryKey(Room record);


    //======@Author sfx===========================
    int insertBatchRoom(List<Room> roomList);
}