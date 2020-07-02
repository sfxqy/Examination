package cn.edu.hbuas.examsys.service.baseinfo;

import cn.edu.hbuas.examsys.dao.StudentDao;
import cn.edu.hbuas.examsys.mybatis.mapper.CollegeMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.ExamPlanMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.StudentMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.*;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.utils.CheckUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生信息管理
 */
@Service
public class Student1Service {
    private static final Logger logger = LoggerFactory.getLogger(Student1Service.class);

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private ExamPlanMapper examPlanMapper;

    /**
     * 分页查询
     * pageNum页面号，pageSize页面条数
     * @param info 模糊查询关键词，可为空
     */
   /* public PageInfo<Student> getAllStudents(Integer pageNum, Integer pageSize, String info)
    {
        if(null==pageNum || pageNum<0)
            pageNum=1;
        PageHelper.startPage(pageNum,pageSize);
        CheckUtil.notNull(pageNum,"页码为空");
        StudentExample studentExample=new StudentExample();
        studentExample.setOrderByClause("cid asc");
        List<Student> list=studentMapper.selectByExample(studentExample);
        PageInfo<Student> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }*/

    /**
     * 根据条件查询学生信息
     * @param student 查询条件
     * @return
     */
    public Student selectStudentByCondition(Student student){
        List<Student> studentList = studentDao.getStudents(student);
        if(studentList.size() <= 0)
            return null;
        return studentList.get(0);
    }



    public Student getStudentByIdcard(Student student)throws Exception{
        List<Student>  students=examPlanMapper.selectArrangeStudent(student);
        if (students.size()==0){
            return null;
        }
        return students.get(0);
    }
    /**
     * 添加学生
     * @param student
     */
    public void addStudent(Student student) throws Exception{
        logger.info("添加的学生信息 :{},sid为{},sname为{} ",student,student.getSid(),student.getSname());
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andSidEqualTo(student.getSid());

        studentExample.or(criteria);
        List<Student> students = studentMapper.selectByExample(studentExample);
        if(students.size() > 0){
            logger.info("学生信息已存在 : "+students.get(0));
            throw new BusinessException("学生信息已经存在");
        }
        studentMapper.insertSelective(student);
    }

    /**
     * 根据学院学号删除学生信息
     * @param sid
     * @throws Exception
     */
    public void deleteStudentById(String sid) throws Exception{
        if (sid==null)
        {
            throw new BusinessException("请选择要删除的学生");
        }
        String[] arr=sid.split(",");
        for (int i=0;i<arr.length;i++)
        {
            if (studentMapper.selectStudentBySid(arr[i])==null)
            {
                throw new BusinessException("不存在该学生");
            }
            studentMapper.deleteBySid(arr[i]);
        }

    }

    /**
     * 根据学院编号删除学生信息
     * @param cid
     * @throws Exception
     */
    public void deleteStudentByCid(Integer cid) throws Exception{
        if (cid==null)
        {
            throw new BusinessException("请输入学院编号");
        }
        if (studentMapper.selectSelfStudent(cid)==null)
        {
            throw new BusinessException("没有学生信息...");
        }
      studentMapper.deleteStudentByCid(cid);
    }

    /**
     * 删除所有学生信息
     * @throws Exception
     */
    public void deleteAllStudent() throws Exception{

        studentMapper.deleteAllStudent();
    }

    /**
     * 修改学生信息
     */
    public void updateStudentInfo(Student student) throws Exception
    {
        if(studentMapper.selectByPrimaryKey(student)==null)
        {
            throw new BusinessException("不存在该学生");
        }
        studentMapper.updateByPrimaryKeySelective(student);
    }

    /**
     * 通过学号查找学生
     * @return
     */
 /*   public Student getStudentById(Student student) throws Exception{
        Student student1=studentMapper.selectByPrimaryKey(student.getSid());
        if(student1==null)
        {
            throw new BusinessException("不存在该学生");
        }
        return student1;
    }*/

    /**
     * 查询全部的学生信息
     * @return
     * @throws Exception
     */
    public List<Student> getAllStudents() throws Exception{
        List<Student> students=studentMapper.selectByExample(null);
        if (students.size()==0)
        {
            throw new BusinessException("没有任何学生信息...");
        }
        return students;
    }

    /**
     * 分页查询所有学生信息
     * pageNum页面号，pageSize页面条数
     * info为模糊查询的参数
     */
    public PageInfo<StudentAndCollege> getAllStudentsByPage(Integer pageNum, Integer pageSize, String sname,Integer cid) throws Exception
    {
        if(pageNum==null||pageNum<0)
            pageNum=1;
        PageHelper.startPage(pageNum,pageSize);

        List<StudentAndCollege> list=studentMapper.selectStudentAndCollege(sname,cid);
        PageInfo<StudentAndCollege> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 分页查询本院学生信息
     * pageNum页面号，pageSize页面条数
     */
    public PageInfo<StudentAndCollege> getSelfStudent(Integer pageNum, Integer pageSize,Integer cid) throws Exception
    {
        if(pageNum==null||pageNum<0)
            pageNum=1;
        PageHelper.startPage(pageNum,pageSize);

        List<StudentAndCollege> list=studentMapper.selectSelfStudent(cid);
        PageInfo<StudentAndCollege> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 根据多个学号查询本院学生信息
     * pageNum页面号，pageSize页面条数
     */
    public List<Student> selectStudents(String sid) throws Exception
    {
        List<Student> students=new ArrayList<>();

        String[] arr=sid.split(",");
        for (int i=0;i<arr.length;i++)
        {
            if (studentMapper.selectStudentBySid(arr[i])==null)
            {
                throw new BusinessException("不存在该学生");
            }
            else
            {
                students.add(studentMapper.selectStudentBySid(arr[i]));
            }

        }
        return students;
    }
}
