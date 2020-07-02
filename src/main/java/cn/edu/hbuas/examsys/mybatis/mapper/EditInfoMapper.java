package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.Surface;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 牛传喜
 */
@Repository
public interface EditInfoMapper {


    /**
     * 查询本院的监考教师安排情况
     * @param tid
     * @param cid
     * @return
     */
    List<Surface> getSurfaceInfoByCid(@Param("tid") int tid, @Param("cid") int cid);

    /**
     * 监考教师的监考次数 -1
     * @param tname
     * @param cid
     */
    void subTeacherTime(@Param("tname") String tname, @Param("cid") Integer cid);

    /**
     * 监考次数+1
     * @param tname
     * @param cid
     */
    int addTeacherTime(@Param("tname") String tname, @Param("cid") Integer cid);

    /**
     * 根据工号减少监考次数
     * @param number
     * @return
     */
    int subTeacherTimeByNumber(@Param("number") String number);

    /**
     * 根据工号添加监考次数 +1
     * @param number
     * @return
     */
    int addTeacherTimeByNumber(@Param("number") String number);

    /**
     * 根据工号获取姓名
     * @param workNumber 教室工号
     * @return
     */
    String getTeacherNameByWorkNumber(@Param("workNumber") String workNumber);

    /**
     * 获取与该场考试同批的所有教师工号
     * @param testtime
     * @param begin
     * @return
     */
    List<String> getAllWorkNumberByTime(@Param("testtime") Date testtime, @Param("begin") Date begin);

    /**
     * 得到所有的教师编号
     * @return
     */
    List<String> getAllWorkNumber();

}
