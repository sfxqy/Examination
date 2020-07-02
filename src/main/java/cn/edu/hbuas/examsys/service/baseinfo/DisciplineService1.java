package cn.edu.hbuas.examsys.service.baseinfo;

import cn.edu.hbuas.examsys.mybatis.mapper.DisciplineMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.NativeMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.StudentMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.*;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.xwpf.usermodel.*;
import java.util.List;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @author 牛传喜
 */

/**
 * 缺考违纪信息管理
 */
@Service
public class DisciplineService1 {

   @Autowired
    private DisciplineMapper disciplineMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private NativeMapper nativeMapper;

    /**
     * 分页查询所有违纪信息
     * @return
     */
    public PageInfo<AgainstTest> getAllDisciplineByPage(Integer pageNum,Integer pageSize) throws Exception
    {
        ToolClass toolClass=new ToolClass();
        if (null==pageNum || pageNum<0)
        {
            pageNum=1;
        }

        PageHelper.startPage(pageNum,pageSize);
        List<AgainstTest> disciplines=disciplineMapper.selectAgainstTestStudents();
        Native n=null;
        for(int i=0;i<disciplines.size();i++)
        {
            String idcard=disciplines.get(i).getIdcard();
            System.out.println(idcard+"身份证");
            String card=toolClass.getIdcard(idcard);
            System.out.println(card+"剪切后的身份证");
            n= nativeMapper.selectByPrimaryKey(card);
            if (n==null)
            {
                throw new BusinessException("没有对应的籍贯信息");
            }
            System.out.println(n.getPlace()+"-------------------");
            disciplines.get(i).setNativePlace(n.getPlace());
        }
        PageInfo<AgainstTest> pageInfo=new PageInfo<>(disciplines);
        return pageInfo;
    }

    /**
     * 查询所有违纪信息
     * @return
     */
    public List<AgainstTest> selectAllAgainstTest() throws Exception
    {
        ToolClass toolClass=new ToolClass();
        List<AgainstTest> disciplines=disciplineMapper.selectAgainstTestStudents();
        Native n=new Native();
        for(int i=0;i<disciplines.size();i++)
        {
            String idcard=disciplines.get(i).getIdcard();
            String card=toolClass.getIdcard(idcard);
            n= nativeMapper.selectByPrimaryKey(card);
            disciplines.get(i).setNativePlace(n.getPlace());
        }
        return disciplines;
    }

    /**
     * 分页查询所有缺考信息
     * @return
     */
    public PageInfo<AgainstTest> getAllMissTest(Integer pageNum,Integer pageSize) throws Exception
    {
        ToolClass toolClass=new ToolClass();
        if (null==pageNum || pageNum<0)
        {
            pageNum=1;
        }
        PageHelper.startPage(pageNum,pageSize);
        List<AgainstTest> disciplines=disciplineMapper.selectMissTestStudents();
        Native n=new Native();
        for(int i=0;i<disciplines.size();i++)
        {
            String idcard=disciplines.get(i).getIdcard();
            String card=toolClass.getIdcard(idcard);
            n= nativeMapper.selectByPrimaryKey(card);
            disciplines.get(i).setNativePlace(n.getPlace());
        }
        PageInfo<AgainstTest> pageInfo=new PageInfo<>(disciplines);
        return pageInfo;
    }


    /**
     * 添加违纪缺考信息前根据学号查询相关信息
     * @return
     */
    public List<AgainstTest1> selectStudentInfo(String sid) throws Exception
    {

        if(studentMapper.selectStudentBySid(sid)==null||sid==null)
        {
            throw new BusinessException("不存在该考生");
        }
        ToolClass toolClass=new ToolClass();
        List<AgainstTest1> disciplines=disciplineMapper.selectStudentInfo(sid);
        Native n=new Native();
        for(int i=0;i<disciplines.size();i++)
        {
            String idcard=disciplines.get(i).getIdcard();
            String card=toolClass.getIdcard(idcard);
            n= nativeMapper.selectByPrimaryKey(card);
            disciplines.get(i).setNativePlace(n.getPlace());
        }
        return disciplines;
    }

    /**
     * 添加缺考违纪信息
     * @param discipline
     */
    public void addDiscipline(Discipline discipline) throws Exception{


        if(studentMapper.selectStudentBySid(discipline.getSid())==null)
        {
            throw new BusinessException("不存在该考生！");
        }
        disciplineMapper.insertSelective(discipline);
    }


    /**
     * 根据主键删除缺考违纪信息
     * @param id
     * @throws Exception
     */
    public void deleteDiscipline(Integer id)throws Exception
    {
        if (disciplineMapper.selectByPrimaryKey(id)==null)
        {
            throw new BusinessException("不存在该缺考信息");
        }
        disciplineMapper.deleteByPrimaryKey(id);
    }



    /**
     * 修改缺考违纪信息
     * @param discipline
     * @throws Exception
     */

    public void updateDiscipline(Discipline discipline) throws Exception
    {
        if (disciplineMapper.selectByPrimaryKey(discipline.getId())==null)
        {
            throw new BusinessException("不存在该违纪信息");
        }
        disciplineMapper.updateByPrimaryKeySelective(discipline);
    }


    /**
     * 生成违纪信报表
     * @param list
     * @throws Exception
     */
    public void creatDisciplineInfo(List<AgainstTest> list,HttpServletResponse resp,HttpServletRequest request) throws IOException
    {
        ToolClass toolClass=new ToolClass();
        //Blank Document
        XWPFDocument document= new XWPFDocument();
        //Write the Document in file system
        FileOutputStream out = new FileOutputStream(
                new File("D:createparagraph.docx"));

        //设置标题
        XWPFParagraph titlePara = document.createParagraph();
        XWPFRun run2=titlePara.createRun();
        String title="关于给予"+list.get(0).getSname()+"等"+list.size()+"位同学纪律处分的决定";
        run2.setBold(true);
        run2.setFontSize(20);
        run2.setText(title);
        titlePara.setIndentationLeft(400);
        run2.setText("\r");

        //设置表头
        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFRun run1=paragraph1.createRun();
        run1.setTextPosition(20);
        run1.setFontSize(14);
        paragraph1.setIndentationFirstLine(400);
        String remark="在学校2018-2019学年上学期 期末考试及补（缓）考考试中，以下学生违反考试纪律，具体如下：";
        run1.setText(remark);

        for(int i=0;i<list.size();i++)
        {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run=paragraph.createRun();
            run.setTextPosition(20);

            int age=0;
            age=toolClass.IdNOToAge(list.get(i).getIdcard());
            list.get(i).setAge(age);
            Date date=list.get(i).getTesttime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);					//放入Date类型数据
            String year,month,day;
            year=String.valueOf(calendar.get(Calendar.YEAR));					//获取年份
            month=String.valueOf(calendar.get(Calendar.MONTH)+1);					//获取月份
            day=String.valueOf(calendar.get(Calendar.DATE));

//            String dateStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);

            /*year=dateStr.substring(0, 4);
            month=dateStr.substring(5, 6);
            day=dateStr.substring(7,9);*/
            //生成违纪信息模板
            String a=list.get(i).getSname()+"，"+list.get(i).getSex()+"，现年"+list.get(i).getAge()+"岁，"+list.get(i).getNativePlace()+
                    "，学号"+list.get(i).getSid()+"，"+list.get(i).getCollege()+list.get(i).getClazz()+"专业"+list.get(i).getGrade()+"级学生" +
                    "，"+"在"+year+"年"+month+"月"+day+"日的"+"《"+list.get(i).getName()+"》"+"课程考试中使用"+list.get(i).getState()+"作弊。\n";
            run.setFontSize(14);
            paragraph.setIndentationFirstLine(400);
            run.setText(a);

        }

        document.write(out);
        out.close();
        System.out.println("createparagraph.docx written successfully");



        //------------------------
//        String dateTime = DateFormatUtils.format(new Date(), "yyMMdd_HHmmss");
        String filepath = "D:createparagraph.docx";


        OutputStream output = null;
        try {
            //清空缓存
            resp.reset();
            // 定义浏览器响应表头，并定义下载名
            String fileName=null;
            fileName = URLEncoder.encode("DisciplineInfo.doc", "UTF-8");
            resp.setHeader("Content-disposition", "attachment;filename="+fileName);
            //定义下载的类型，标明是word文件
            resp.setContentType("application/msword;charset=UTF-8");
            //把创建好的word写入到输出流

            FileInputStream inputStream = new FileInputStream(filepath);
            byte[] bufferBytes = new byte[inputStream.available()];

            inputStream.read(bufferBytes);

            output = resp.getOutputStream();
            output.write(bufferBytes);

            //随手关门
            output.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        //------------------------
    }






}
