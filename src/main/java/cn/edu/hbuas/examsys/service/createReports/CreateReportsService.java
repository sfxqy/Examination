package cn.edu.hbuas.examsys.service.createReports;


import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.mapper.CollegeMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.ExamcourseMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.ReportMapper;
import cn.edu.hbuas.examsys.mybatis.mapper.TaskplanMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.*;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import cn.edu.hbuas.examsys.utils.FileUtil;
import cn.edu.hbuas.examsys.utils.ReportUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author SFX
 */
@Service
public class CreateReportsService {


    private static final Logger logger = LoggerFactory.getLogger(CreateReportsService.class);

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private TaskplanMapper taskplanMapper;


    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private ExamcourseMapper examcourseMapper;


    /**
     * 生成门贴
     * @param session
     * @param response
     * @param tid
     * @param cid
     * @param grade
     * @param clazz
     * @param name
     * @throws Exception
     */
    public void createDoorPost(HttpSession session, HttpServletResponse response,Integer tid, Integer cid, String grade, String clazz, String name){
        User user=(User) session.getAttribute("user");
        logger.info("开始生成门贴  参数 user：{}",user.getAccount());
        String path="DoorPost";
        String realPath=ReportUtil.createFile(session,path);
        logger.info("路径：{}",realPath);
        List<String> paths=new ArrayList<>();

        session.setAttribute("DoorPost","0");

        try{
            if(user.getCid()==0||user.getCid()==-1||user.getCid()==cid){
                if (user==null||user.getCid()==-1||user.getCid()==0)cid=null;
                if (grade==null||grade.length()==0)grade=null;
                if (clazz==null||clazz.length()==0)clazz=null;
                if (name==null||name.length()==0)name=null;

                List<DoorPost> doorPostList=reportMapper.getDoorPostInfo(tid,cid,grade,clazz,name);
                logger.info("数据量："+doorPostList.size());
                if(doorPostList.size()==0){
                    throw new BusinessException("未查询到相关数据");
                }
                for(DoorPost d:doorPostList){
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    String p= File.separator +d.getCname()+"-"+d.getName()+"-"+d.getClazz()+"-"+d.getRid()+"  "+uuid+".pdf";
                    logger.info("路径：{}"+p);
                    String pa=File.separator+"PDF"+File.separator+"DoorPost"+p;
                    ReportUtil.createDoorPost(realPath+p,d);
                    paths.add(pa);
                }
                //将文件压缩并下载
                ReportUtil.fileDownload(session,response,paths,3,"门贴");
            }else{
                logger.info("权限不够，禁止相关操作");
                throw new BusinessException("权限不够，禁止相关操作");
            }

        }catch (Exception e){
            session.setAttribute("DoorPost","-1");
            e.printStackTrace();
        }finally {
            if (session.getAttribute("DoorPost").equals("0")){
                session.setAttribute("DoorPost","1");
            }
        }




        logger.info("门贴生成并下载成功");
    }

    /**
     * 考试安排表
     * @param session
     * @param response
     * @param tid
     * @throws Exception  roomArrangement
     */
    public void createRoomArrangement(HttpSession session, HttpServletResponse response,Integer tid){
        List<String> data1=reportMapper.getDataTime();
        List<String> data2=reportMapper.getTime();
        List<RoomArrangement> roomArrangements=null;
        String path="roomArrangement";
        String realPath=ReportUtil.createFile(session,path);
        List<String> paths=new ArrayList<>();
        for(int i=0;i<data1.size();i++){
            for(int j=0;j<data2.size();j++){
                roomArrangements=reportMapper.createRoomArrangement(data1.get(i),data2.get(j),tid);
                logger.info("数据量：{}，{}，{}",roomArrangements.size(),data1.get(i),data2.get(j));
                //调用生成排表方法
                if (roomArrangements.size()==0)continue;
                int a=roomArrangements.size()/30;
                int b=roomArrangements.size()%30;
                //计算出该时间段有多少张安排表
                if (b!=0) a++;
                for (int z=0;z<a;z++){
                    int n=29;
                    if (roomArrangements.size()<30){
                        n=roomArrangements.size();
                    }
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    logger.info("时间参数：{}",roomArrangements.get(z).getTime());
                    String p= File.separator +"考试安排表 "+" "+roomArrangements.get(z).getTime().replace(":","")+"-"+roomArrangements.get(z).getRid()+"  "+uuid+".pdf";
                    String pa=File.separator+"PDF"+File.separator+"roomArrangement"+p;

                    paths.add(pa);
                    ReportUtil.roomArrangement(roomArrangements.subList(0,n),realPath+p);
                    logger.info("生成文件路径2：{}",realPath+p);
                    roomArrangements.removeAll(roomArrangements.subList(0,n));

                }
                //将集合置空
                roomArrangements=null;
            }
        }

        for (String s:paths){
            logger.info("循环路径：{}",s);
        }
        //将文件压缩并下载
        session.setAttribute("roomArrangement","0");
        try {
            ReportUtil.fileDownload(session,response,paths,3,"考试安排");
        }catch (Exception e){
            session.setAttribute("roomArrangement","-1");
        }finally {
            if (session.getAttribute("roomArrangement").equals("0")){
                session.setAttribute("roomArrangement","1");
            }
        }
    }



    /** 生成学生信息表  包含签到
     * @param session
     * @param response
     * @param cid
     * @param tid
     * @throws Exception
     */
    public void createStudentStatement(HttpSession session, HttpServletResponse response,Integer cid,Integer tid){
        List<StudentStatement> studentStatementInfo=reportMapper.getStudentStatementInfo(cid,tid);
        String path="StudentStatement";
        String realPath=ReportUtil.createFile(session,path);
        List<String> paths=new ArrayList<>();
        for(StudentStatement s:studentStatementInfo){
            List<AdmissionCard> admissionCards=reportMapper.getStudentAdmissionCard(tid,s.getSuid());
            s.setAdmissionCardList(admissionCards);
        }
        for(StudentStatement s:studentStatementInfo){
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String p= File.separator +s.getName()+"-"+s.getRid()+"  "+uuid+".pdf";
            String pa=File.separator+"PDF"+File.separator+"StudentStatement"+p;
            ReportUtil.createStudentStatement(realPath+p,s,session);
            paths.add(pa);
        }


        session.setAttribute("studentStatement","0");
        try {
            ReportUtil.fileDownload(session,response,paths,3,"学生信息");
        }catch (Exception e){
            session.setAttribute("studentStatement","-1");
        }finally {
            if (session.getAttribute("studentStatement").equals("0")){
                session.setAttribute("studentStatement","1");
            }
        }

    }




    /**
     * 生成准考证
     * @param session
     * @param response
     * @param tid
     * @param cid
     * @throws Exception
     */
    public void admissionTicket(HttpSession session, HttpServletResponse response,Integer tid,Integer cid){
        String path="Ticket";
        List<AdmissionCard> admissionCards=reportMapper.getAdmissionCard(tid,cid);
        logger.info("数据量{},tid{},cid{}",admissionCards.size(),tid,cid);
        List<String> paths=new ArrayList<>();
        String title=taskplanMapper.getTitle(tid);
        logger.info("title:{}",title);
        String realPath=null;
        logger.info("服务器paths路径：{}",realPath);
        for (AdmissionCard a:admissionCards){
            path="Ticket"+File.separator+a.getCollege()+File.separator+a.getClazz();
            List<Details> details=reportMapper.getDetailsBySidAndTid(tid,a.getSid());
            a.setDetails(details);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String p=File.separator +a.getClazz()+"-"+a.getSid()+"   "+uuid+".pdf";
            String pa=File.separator+"PDF"+File.separator+"Ticket"+File.separator+a.getCollege()+File.separator+a.getClazz()+p;
            logger.info("文件路径：{}",path+p);
            realPath=ReportUtil.createFile(session,path);
            System.out.println("真实路径："+realPath);
            ReportUtil.admissionTicket(a,realPath+p,title,session);
            paths.add(pa);
        }
        session.setAttribute("admissionTicket","0");
        try {
            ReportUtil.fileDownload(session,response,paths,5,"准考证");
        }catch (Exception e){
            session.setAttribute("admissionTicket","-1");
        }finally {
            if (session.getAttribute("admissionTicket").equals("0")){
                session.setAttribute("admissionTicket","1");
            }
        }


    }






    /**
     *  考试情况记载表
     * @param session
     * @param response
     * @throws Exception
     */
    public void examDetails(HttpSession session,HttpServletResponse response){
        String path="examDetails";
        String realPath=ReportUtil.createFile(session,path);
        String p=File.separator +"考试情况记载表.pdf";
        String pa=File.separator+"PDF"+File.separator+"examDetails"+p;
        List<String> paths=new ArrayList<>();
        paths.add(pa);
        ReportUtil.examDetails(realPath+p);

        session.setAttribute("details","0");
        try {
            ReportUtil.fileDownload(session,response,paths,3,"考试情况记载");
        }catch (Exception e){
            session.setAttribute("details","-1");
        }finally {
            if (session.getAttribute("details").equals("0")){
                session.setAttribute("details","1");
            }
        }

    }



    /**
     *  考生座位对照表
     * @param session
     * @param response
     * @param tid
     * @throws Exception
     */
    public void contrast(HttpSession session,HttpServletResponse response,Integer tid){
        String path="contrast";
        String realPath=ReportUtil.createFile(session,path);
        List<String> paths=new ArrayList<>();
        List<Contrast> contrast=reportMapper.getContrast(tid);
        logger.info("开始生成对照表 参数{}",tid);
        for (Contrast c:contrast){
            List<Details> details= reportMapper.getDetailsforContrast(c.getSuid());
            c.setDetails(details);
        }
        for (Contrast c:contrast){
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String p= File.separator +c.getCname()+"-"+c.getEname()+"-"+c.getRid()+"  "+uuid+".pdf";
            String pa=File.separator+"PDF"+File.separator+"contrast"+p;
            ReportUtil.contrast(c,realPath+p);
            paths.add(pa);
        }


        session.setAttribute("contrast","0");
        try {
            ReportUtil.fileDownload(session,response,paths,3,"考生座位对照");
        }catch (Exception e){
            session.setAttribute("contrast","-1");
        }finally {
            if (session.getAttribute("contrast").equals("0")){
                session.setAttribute("contrast","1");
            }
        }



    }




    public void createAroundTest(Integer tid,Integer cid,HttpServletResponse response,HttpSession session) throws Exception{
        isOurHospital( session, cid);
        String [] title=new String[]{"考场编号","学院","班级","考试科目名称","地点","考试时间","监考教师","教师工号（如若有同名请参考工号）"};
        List<AroundTest> aroundTests=reportMapper.getAroundTest(tid,cid);
        System.out.println(aroundTests.get(0).getWorknumber());
        String fileName=null;
        if (cid!=null){
            College college=collegeMapper.selectByPrimaryKey(cid);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName=college.getCollege()+"监考安排表  "+uuid+".xlsx";
        }else {
            fileName="监考安排表.xlsx";
        }
        FileUtil.getHSSFWorkbook( title,aroundTests,response,fileName);
    }



    public void isOurHospital(HttpSession session,Integer cid)throws Exception{
        User user=(User)session.getAttribute("user");
        if (user.getCid()!=0&&user.getCid()!=-1&&user.getCid()!=cid){
            throw new BusinessException("禁止查询非本院相关信息");
        }
    }



    public void getInfoForStudent(HttpServletResponse response,HttpSession session,Integer tid,Integer cid)throws Exception{
        isOurHospital( session, cid);
        String [] title=new String[]{"班级","考试科目名称","地点","考试时间","监考教师"};
        List<AroundTest> aroundTests=reportMapper.getInfoForStudent(tid,cid);
        if (aroundTests.size()==0){
            throw new BusinessException("暂无数据");
        }
        String fileName=null;
        if (cid!=null){
            College college=collegeMapper.selectByPrimaryKey(cid);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName=college.getCollege()+"考试安排表  "+uuid+".xlsx";
        }else {
            fileName="监考安排表.xlsx";
        }
        FileUtil.getHSSFWorkbook( title,aroundTests,response,fileName);
    }



    public void Excel(HttpSession session,Integer tid,HttpServletResponse response)throws Exception{
        List<String> data1=reportMapper.getDataTime();
        List<String> data2=reportMapper.getTime();

        List<RoomArrangement> ss=new ArrayList<>();
        String path="roomArrangement";
        String realPath=ReportUtil.createFile(session,path);
        List<String> paths=new ArrayList<>();
        for(int i=0;i<data1.size();i++){
            for(int j=0;j<data2.size();j++){
                logger.info("参数data1 {}  参数2{}",data1.get(i),data2.get(i));
                List<RoomArrangement> roomArrangements=reportMapper.createRoomArrangements(data1.get(i),data2.get(j),tid);
                logger.info("数据量：{}",roomArrangements.size());
                ss.addAll(roomArrangements);

            }
        }
        String [] title=new String[]{"考场编号","学院","考试科目","具体教室","考场人数","监考教师"};
        FileUtil.getHSSFWorkbook1( title,ss,response,"考试安排.xlsx");



    }

    /**pby
     * @param tid
     * @param cid 院号
     * @param servletResponse
     */
    public void printPDF(Integer tid, Integer cid, HttpServletResponse servletResponse) throws DocumentException {
        //获取需要的数据
        ExamcourseExample examcourseExample = new ExamcourseExample();
        ExamcourseExample.Criteria criteria = examcourseExample.createCriteria();
        criteria.andCidEqualTo(cid);
        criteria.andTidEqualTo(tid);
        List<Examcourse> examcourses = examcourseMapper.selectByExample(examcourseExample);
        CollegeExample example = new CollegeExample();
        CollegeExample.Criteria collegeCriteria = example.createCriteria();
        collegeCriteria.andCidEqualTo(cid);
        List<College> colleges = collegeMapper.selectByExample(example);
        //生成pdf
        String path = colleges.get(0).getCollege()+".pdf";
        File file = cn.hutool.core.io.FileUtil.touch(path);
        Document document = new Document();
        //中文字体
        BaseFont bfChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font bigFontChinese = new Font(bfChinese, 30, Font.BOLD);
        Font smallFontChinese = new Font(bfChinese, 18, Font.BOLD);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        document.addCreationDate();
        //标题
        String fileName = colleges.get(0).getCollege()+"教学秘书签字表";
        
        Paragraph title = new Paragraph(fileName,bigFontChinese);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setFont(bigFontChinese);
        title.setPaddingTop(5f);
        document.add(title);
        //表格内容
        PdfPTable table = new PdfPTable(4);
        // 宽度100%填充
        table.setWidthPercentage(100);
        // 前间距
        table.setSpacingBefore(2f);
        // 后间距
        table.setSpacingAfter(2f);
        ArrayList<PdfPRow> rows = table.getRows();
        //设置列宽
        float[] columnWidths = {2f, 3f, 2f,1f};
        table.setWidths(columnWidths);

        PdfPCell[] cells1 = new PdfPCell[4];
        PdfPRow row1 = new PdfPRow(cells1);

        cells1[0] = new PdfPCell(new Paragraph("班级",smallFontChinese));//单元格内容
        cells1[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells1[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells1[0].setMinimumHeight(40);



        cells1[1] = new PdfPCell(new Paragraph("科目",smallFontChinese));
        cells1[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells1[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells1[1].setMinimumHeight(40);

        cells1[2] = new PdfPCell(new Paragraph("考试形式",smallFontChinese));
        cells1[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells1[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells1[2].setMinimumHeight(40);

        cells1[3] = new PdfPCell(new Paragraph("人数",smallFontChinese));
        cells1[3].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells1[3].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
        cells1[3].setMinimumHeight(40);

        
        rows.add(row1);

        for (Examcourse examcours : examcourses) {
            PdfPCell[] cells = new PdfPCell[4];
            PdfPRow row = new PdfPRow(cells);

            cells[0] = new PdfPCell(new Paragraph(examcours.getClazz(),smallFontChinese));//单元格内容
            cells[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells[0].setMinimumHeight(40);

            cells[1] = new PdfPCell(new Paragraph(examcours.getName(),smallFontChinese));
            cells[1].setHorizontalAlignment(Element.ALIGN_CENTER);
            cells[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
            cells[1].setMinimumHeight(40);

            cells[2] = new PdfPCell(new Paragraph(examcours.getTestForm(),smallFontChinese));
            cells[2].setHorizontalAlignment(Element.ALIGN_CENTER);
            cells[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
            cells[2].setMinimumHeight(40);

            cells[3] = new PdfPCell(new Paragraph(examcours.getNumber().toString(),smallFontChinese));
            cells[3].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells[3].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells[3].setMinimumHeight(40);
            rows.add(row);
        }
        document.add(table);
        //签名
        Paragraph footer = new Paragraph("签名：                ",smallFontChinese);
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.add(footer);

        document.close();
        //传输文件
        FileInputStream is = null;
        BufferedInputStream bs = null;
        OutputStream os = null;
        try {
            servletResponse.setHeader("Content-Type", "application/octet-stream");
            servletResponse.setHeader("Content-Disposition", "attachment;filename=" + new String(path.getBytes(
                    "gb2312"), "ISO8859-1"));
            is = new FileInputStream(file);
            bs = new BufferedInputStream(is);
            os = servletResponse.getOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = bs.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        }catch (IOException e){

        }finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (bs != null) {
                    bs.close();
                }
                if (os != null) {
                    os.flush();
                    os.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        cn.hutool.core.io.FileUtil.del(file);
    }
}
