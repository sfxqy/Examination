package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.*;
import cn.edu.hbuas.examsys.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 牛传喜
 */
@Repository
public class EditInfoDao {

    @Autowired
    private SurfaceMapper surfaceMapper;
    @Autowired
    private EditInfoMapper editInfoMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private ExamcourseMapper examcourseMapper ;

    /**
     * @param tid
     * @param cid
     * @return
     */
    public List<Surface> getSurfaceInfoByCid(int tid, int cid) {
        return editInfoMapper.getSurfaceInfoByCid(tid, cid);
    }

    /**
     * 根据教师姓名，以及学院id减少一次该教师的监考次数
     *
     * @param s
     * @param cid
     */
    public void subTeacherTime(String s, Integer cid) {
        editInfoMapper.subTeacherTime(s, cid);
    }

    /**
     * 监考教师的监考次数 + 1
     *
     * @param s
     * @param cid
     */
    public int addTeacherTime(String s, Integer cid) {
        return editInfoMapper.addTeacherTime(s, cid);
    }

    public int updateSurface(Surface surface) {
        return surfaceMapper.updateByPrimaryKeySelective(surface);
    }

    /**
     * 根据工号减少监考次数 -1
     * @param number
     * @return
     */
    public int subTeacherTimeByNumber(String number){
     return editInfoMapper.subTeacherTimeByNumber(number);
    }

    /**
     * 根据工号添加监考次数 +1
     * @param number
     * @return
     */
    public int addTeacherTimeByNumber(String number){
        return  editInfoMapper.addTeacherTimeByNumber(number);
    }

    /**
     * 查询本次考试任务下的所有教室
     * @param tid
     * @return
     */
    public List<Room> selectRoomByTid(int tid) {
        RoomExample roomExample =  new RoomExample();
        RoomExample.Criteria criteria = roomExample.createCriteria();
        criteria.andTidEqualTo(tid);
        return roomMapper.selectByExample(roomExample);
    }

    /**
     * 查询本院本次考试的所有考试科目
     * @param cid
     * @param tid
     * @return
     */
    public List<Examcourse> selectExamcourseByCid(int cid, int tid) {
        ExamcourseExample examcourseExample = new ExamcourseExample();
        ExamcourseExample.Criteria criteria = examcourseExample.createCriteria();
        criteria.andTidEqualTo(tid);
        criteria.andCidEqualTo(cid);
        return examcourseMapper.selectByExample(examcourseExample);
    }

//    public List<Surface> getSurfaceInfoByTime(Date testtime, Date begin) {
//        return null;
//    }
}
