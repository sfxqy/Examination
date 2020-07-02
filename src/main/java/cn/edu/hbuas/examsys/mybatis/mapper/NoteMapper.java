package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.NoteDTO;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author panboyuan
 */
@Repository
public interface NoteMapper {

    List<NoteDTO> selectTeacherArrangeByTid(@Param("tid") int tid);

    /**
     * 根据考试任务查询出本次参加监考的所有教师姓名和电话和工号
     * @param tid 查询主键
     * @return
     */
    List<Teacher> selectInvigilationInfoByTid(@Param("tid") int tid);

    /**
     * 查询出学位考试的所有监考安排
     * @param tidOne
     * @param tidTwo
     * @return
     */
    List<NoteDTO> selectDegreeArrangeByTid(@Param("tidOne") int tidOne, @Param("tidTwo") Integer tidTwo);

    /**
     * 根据日期，查询出本日的所有考试安排
     * @param time
     * @param tid
     * @return
     */
    List<NoteDTO> selectTeacherArrangeByTime(@Param("time") String time, @Param("tid") int tid);

    /**
     * 根据传入的时候查询是否有监考安排
     * @param time
     * @return 监考安排的数量
     */
    Integer haveExam(@Param("time") String time);

    /**
     * 根据日期查询当日的考试任务的tid
     * @param time
     * @return
     */
    Integer getTidByTime(@Param("time") String time);
}
