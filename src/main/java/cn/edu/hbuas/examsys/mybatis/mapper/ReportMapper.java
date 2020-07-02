package cn.edu.hbuas.examsys.mybatis.mapper;

import cn.edu.hbuas.examsys.mybatis.pojo.*;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author SFX
 * 报表Mapper
 */
@Repository
public interface ReportMapper {


    /**
     * 门贴
     * @param tid
     * @param cid
     * @param grade
     * @param clazz
     * @param name
     * @return
     */
    List<DoorPost> getDoorPostInfo(@Param("tid") Integer tid, @Param("cid") Integer cid, @Param("grade") String grade, @Param("clazz") String clazz, @Param("name") String name);


    /**
     * 考场安排
     * @param time1
     * @param begin
     * @return
     */
    List<RoomArrangement> createRoomArrangement(@Param("time1") String time1, @Param("begin") String begin,@Param("tid") Integer tid);

    List<RoomArrangement> createRoomArrangements(@Param("time1") String time1, @Param("begin") String begin,@Param("tid") Integer tid);


    List<String> getDataTime();

    List<String> getTime();

    /**
     * 查询考试信息册的表头信息
     * @return
     */
    List<StudentStatement> getStudentStatementInfo(@Param("cid") Integer cid,@Param("tid") Integer tid);


    List<AdmissionCard> getStudentAdmissionCard(@Param("tid") Integer tid, @Param("suid") Integer suid);


    List<AdmissionCard> getAdmissionCard(@Param("tid") Integer tid, @Param("cid") Integer cid);


    List<Details> getDetails(@Param("tid") Integer tid);

    List<Details> getDetailsBySidAndTid(@Param("tid") Integer tid,@Param("sid") String sid);


    List<Contrast> getContrast(@Param("tid") Integer tid);


    List<Details> getDetailsforContrast(@Param("suid") Integer suid);

    List<AroundTest> getAroundTest(@Param("tid") Integer tid,@Param("cid") Integer cid);


    List<AroundTest> getInfoForStudent(@Param("tid") Integer tid,@Param("cid") Integer cid);

}
