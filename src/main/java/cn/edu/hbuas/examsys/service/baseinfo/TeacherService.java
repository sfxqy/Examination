package cn.edu.hbuas.examsys.service.baseinfo;

import cn.edu.hbuas.examsys.mybatis.mapper.TeacherMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import cn.edu.hbuas.examsys.mybatis.pojo.TeacherAndRole;
import cn.edu.hbuas.examsys.mybatis.pojo.TeacherExample;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 牛传喜
 */

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 添加教师信息
     */
    public void addTeacher(Teacher teacher) throws Exception
    {
        TeacherExample teacherExample=new TeacherExample();
        TeacherExample.Criteria criteria=teacherExample.createCriteria();
        criteria.andNumberEqualTo(teacher.getNumber());

        TeacherExample.Criteria criteria11=teacherExample.createCriteria();
        criteria11.andWagesEqualTo(teacher.getWages());

        teacherExample.or(criteria);
        teacherExample.or(criteria11);
        List<Teacher> teacherList=teacherMapper.selectByExample(teacherExample);
        if(teacherList.size()>0)
        {
            throw new BusinessException("教师信息已存在");
        }
        teacherMapper.insertSelective(teacher);
    }


    /**
     * 删除教师信息
     */
    public void deleteTeacher(String str) throws Exception
    {
        int tid;
        String[] arr=str.split(",");
        for (int i=0;i<arr.length;i++)
        {
            tid=Integer.valueOf(arr[i]);
            if (teacherMapper.selectByPrimaryKey(tid)==null)
                throw new BusinessException("不存在该教师");
            else
                teacherMapper.deleteByPrimaryKey(tid);
        }
    }

    /**
     * 删除某个院的教师信息
     */
    public void deleteTeacherByCid(Integer cid) throws Exception
    {
      if (cid==null) {
          throw new BusinessException("请输入院别编号");
      }
      teacherMapper.deleteTeacherByCid(cid);
    }
    /**
     * 删除所有教师信息
     */
    public void deleteAllTeacher() throws Exception
    {

        teacherMapper.deleteAllTeacher();
    }
    /**
     * 修改教师信息
     */
    public void updateTeacherInfo(Teacher teacher) throws Exception
    {

        if (teacherMapper.selectByPrimaryKey(teacher.getTid())==null)
            throw new BusinessException("不存在该教师");
        teacherMapper.updateByPrimaryKey(teacher);
    }

    /**
     * 根据id查询教师信息教师信息
     */
    public Teacher selectTeacherById(Teacher teacher) throws Exception
    {
        Teacher teacher1=teacherMapper.selectByPrimaryKey(teacher.getTid());
        if (teacher1==null)
        {
            throw new BusinessException("不存在该教师");
        }
       return teacher1;
    }

    /**
     * 根据tid字符串查询多个教师信息
     */
    public List<Teacher> selectTeachers(String str) throws Exception
    {
        List<Teacher> list=new ArrayList<>();
       int tid=0;
        String[] arr=str.split(",");
        for (int i=0;i<arr.length;i++)
        {
            tid=Integer.valueOf(arr[i]);
            if (teacherMapper.selectByPrimaryKey(tid)==null)
                throw new BusinessException("不存在该教师");
            else
            {
                list.add(teacherMapper.selectByPrimaryKey(tid));
            }

        }
        return list;
    }

    /**
     * 分页查询本院所有教师信息
     *
     */
    public PageInfo<TeacherAndRole> selectSelfTeacherByPage(Integer pageNum,Integer pageSize,Integer cid) throws Exception
    {
        if(null==pageNum || pageNum<0)
            pageNum=1;
        PageHelper.startPage(pageNum,pageSize);
        List<TeacherAndRole> list=teacherMapper.selectSelfTeacher(cid);
        if (list.size()==0)
        {
            throw new BusinessException("没有任何教师信息...");
        }
        PageInfo<TeacherAndRole> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 不分页查询本院所有教师信息
     *
     */
    public List<TeacherAndRole> selectSelfTeacher(Integer cid) throws Exception
    {
        if (cid==null)
        {
            throw new BusinessException("请输入院别编号");
        }
        List<TeacherAndRole> list=teacherMapper.selectSelfTeacher(cid);
        System.out.println("++++++++++++++"+list.size());
        if (list.size()==0)
        {
            throw new BusinessException("没有任何教师信息...");
        }
        return list;
    }

    /**
     * 分页查询所有教师信息
     * pageNum页面号，pageSize页面条数
     * info为模糊查询的参数
     */
    public PageInfo<TeacherAndRole> getAllTeachersByPage(Integer pageNum,Integer pageSize,Integer cid,String tname,String number) throws Exception
    {
        if(null==pageNum || pageNum<0)
            pageNum=1;
        PageHelper.startPage(pageNum,pageSize);
        /*TeacherExample teacherExample=new TeacherExample();
        TeacherExample.Criteria criteria=teacherExample.createCriteria();
       if (tname!=null)
            criteria.andTnameLike("%"+tname+"%");
        List<Teacher> teacherList=teacherMapper.selectByExample(teacherExample);*/
        List<TeacherAndRole> list=teacherMapper.selectTeacherAndRole(cid,tname, number);
        PageInfo<TeacherAndRole> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
}
