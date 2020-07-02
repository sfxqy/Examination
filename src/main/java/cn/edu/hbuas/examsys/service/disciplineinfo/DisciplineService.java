package cn.edu.hbuas.examsys.service.disciplineinfo;


import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.mapper.DisciplineMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.StudentMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Discipline;
import cn.edu.hbuas.examsys.mybatis.pojo.Student;
import cn.edu.hbuas.examsys.mybatis.pojo.StudentKey;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author SFX
 */
@Service
public class DisciplineService {

    private static final Logger logger = LoggerFactory.getLogger(DisciplineService.class);


    @Autowired
    private DisciplineMapper disciplineMapper;

    @Autowired
    private StudentMapper studentMapper;


    /**
     * 缺考违纪情况
     * @param discipline
     * @throws Exception
     */
    public void disciplineInfo(Discipline discipline)throws  Exception{
        StudentKey studentKey=null;
        studentKey.setSid(discipline.getSid());
        Student student=studentMapper.selectByPrimaryKey(studentKey);
        if (student.getSid()==null) {
            logger.info("[查无此人，参数{}]",discipline.getSid());
            throw new BusinessException("查无此人");
        }
        int row=disciplineMapper.insert(discipline);
        if (row==0){
            logger.info("[系统异常，信息录入失败 录入参数{}]",discipline.getSid());
            throw new BusinessException("系统异常，信息录入失败");
        }
    }


    /**
     * 删除缺考违纪情况
     * @param did
     * @throws Exception
     */
    public void deleteDisciplineInfo(Integer did)throws Exception{
        int row=disciplineMapper.deleteByPrimaryKey(did);
        if (row==0){
            logger.info("[系统异常，信息删除失败，参数{}]",did);
            throw new BusinessException("系统异常，删除失败，请重新再试");
        }
    }





    public void updateDisciplineInfo(Discipline discipline)throws Exception{
        disciplineMapper.updateByPrimaryKey(discipline);
    }


    public List<Discipline> getDisciplineInfo(HttpSession session,Integer pageno,Integer pages, Integer cid, String name,String sid,Integer suid){
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=0){
            cid=user.getCid();
        }else{

        }


        return null;

    }



}
