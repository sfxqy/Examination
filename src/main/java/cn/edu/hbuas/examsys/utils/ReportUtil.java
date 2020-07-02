package cn.edu.hbuas.examsys.utils;

import cn.edu.hbuas.examsys.mybatis.pojo.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author SFX
 */
public class ReportUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReportUtil.class);


    /**
     * 判断要文件是否存在，若不存在则创建，存在则不创建
     * @param session
     * @param path
     * @return 0+path 文件为新建  1+path 文件已经存在了
     */
    public static String createFile(HttpSession session,String path){
        String directory = session.getServletContext().getRealPath(File.separator+"PDF"+File.separator+path);
        File directoryFile=new File(directory);
        if(!directoryFile.isDirectory() && !directoryFile.exists()){
            directoryFile.mkdirs();
        }else {
            FileUtil.deleteFile(session,File.separator+"PDF"+File.separator+path);
            directoryFile.mkdirs();
        }
        return directory;
    }


    /**
     *
     * @param session
     * @param path
     * @return
     */
    public static String createFile1(HttpSession session,String path){
        String directory = session.getServletContext().getRealPath(File.separator+"PDF"+File.separator+path);
        File directoryFile=new File(directory);
        if(!directoryFile.isDirectory() && !directoryFile.exists()){
            directoryFile.mkdirs();
        }
        return directory;
    }

    //生成门贴
    public static void createDoorPost(String path,DoorPost doorPost) {
        logger.info("开始生成门贴 生成路径：{}",path);
        Document document = new Document(PageSize.A4.rotate(),120,20,20,20);
        try {
            //中文字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese, 35, Font.BOLD);
            Font FontChinese1 = new Font(bfChinese, 30, Font.BOLD);
            //写入器
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            Paragraph p1 = new Paragraph(doorPost.getTitle(), FontChinese);
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setFont(FontChinese);
            document.add(p1);

            Paragraph p2= new Paragraph("院          别：  "+doorPost.getCname(), FontChinese1);
            p2.setSpacingAfter(20);
            p2.setFont(FontChinese1);
            document.add(p2);

            Paragraph p4= new Paragraph("年          级：  "+doorPost.getGrade(), FontChinese1);
            p4.setSpacingAfter(20);
            p4.setFont(FontChinese1);
            document.add(p4);

           /* Paragraph p3= new Paragraph("班          级：  "+doorPost.getClazz(), FontChinese1);
            p3.setSpacingAfter(20);
            p3.setFont(FontChinese1);
            document.add(p3);
*/
            Paragraph p5= new Paragraph("考试科目：  "+doorPost.getName(), FontChinese1);
            p5.setSpacingAfter(20);
            p5.setFont(FontChinese1);
            document.add(p5);

            Paragraph pph2 = new Paragraph("考试时间：  "+doorPost.getTime(), FontChinese1);
            pph2.setSpacingAfter(20);
            pph2.setFont(FontChinese1);
            document.add(pph2);

            Paragraph pph3 = new Paragraph("考试地点：  "+doorPost.getPlace(), FontChinese1);
            pph3.setSpacingAfter(20);
            pph3.setFont(FontChinese1);
            document.add(pph3);

            Paragraph pph4 = new Paragraph("考场编号：  "+doorPost.getRid(), FontChinese1);
            pph4.setSpacingAfter(20);
            pph4.setFont(FontChinese1);
            document.add(pph4);


           /* Paragraph pph5 = new Paragraph("监考教师：  "+doorPost.getTeacher(), FontChinese1);
            pph5.setSpacingAfter(20);
            pph5.setFont(FontChinese1);
            document.add(pph5);*/
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //生成学生信息表  包含签到
    public static void createStudentStatement(String pa,StudentStatement s,HttpSession session) {
        Document document = new Document(PageSize.A4,10,10,0,0);
        try {
            //中文字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese, 13, Font.NORMAL);
            Font FontChinese1 = new Font(bfChinese, 8, Font.NORMAL);
            Font FontChinese3 = new Font(bfChinese, 10, Font.NORMAL);
            //写入器
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pa));
            document.open();

            Paragraph pph1 = new Paragraph("考生信息册", FontChinese);

            pph1.setAlignment(Element.ALIGN_CENTER);
            pph1.setFont(FontChinese);

            Paragraph pph2 = new Paragraph("考场编号: "+s.getRid()+"     教室: "+s.getPlace()+"     考试科目: "+s.getName()+"     时间:"+s.getTime()/*+"     监考教师: "+s.getTeacher()*/, FontChinese3);
            pph2.setAlignment(Element.ALIGN_CENTER);
            pph2.setFont(FontChinese);
            pph2.setSpacingAfter(2);
            document.add(pph1);
            document.add(pph2);
            List<AdmissionCard> admissionCard=s.getAdmissionCardList();
            int size=admissionCard.size();
            //计算多少行数据
            int row=size/6;
            //计算最后一行有多少条数据
            int lastrow=size%6;
            if (lastrow!=0){
                    row=row+1;
                }else {
                    lastrow=6;
                }
            int count=0;
            logger.info("行数：{}",row);
            for(int i=0;i<row;i++){

                if (i<row-1){
                    PdfPTable table = new PdfPTable(6);
                    table.setWidthPercentage(100); // 宽度100%填充
                    table.setSpacingBefore(2f); // 前间距
                    table.setSpacingAfter(2f); // 后间距
                    List<PdfPRow> listRow = table.getRows();
                    float[] columnWidths = {1f, 1f, 1f,1f,1f,1f};
                    table.setWidths(columnWidths);

                    PdfPCell[] cells1=new PdfPCell[6];
                    PdfPCell[] cells2=new PdfPCell[6];
                    PdfPCell[] cells3=new PdfPCell[6];
                    PdfPCell[] cells4=new PdfPCell[6];
                    PdfPCell[] cells5=new PdfPCell[6];
                    PdfPRow row1=new PdfPRow(cells1);
                    PdfPRow row2=new PdfPRow(cells2);
                    PdfPRow row3=new PdfPRow(cells3);
                    PdfPRow row4=new PdfPRow(cells4);
                    PdfPRow row5=new PdfPRow(cells5);

                    for(int j=0;j<6;j++){
                            int n = 6 * i + j;

                        cells1[j] =null; //单元格内容
                        String path =admissionCard.get(n).getPhoto();
                        logger.info("path:{}",path==null);
                        //测试时注释
                        if (path != null) {
                            logger.info("进入");
                            String photo = session.getServletContext().getRealPath(path);
                            File file=new File(photo);
                            if (file.exists()){
                                Image image = Image.getInstance(photo);
                                image.scaleToFit(90,210);
                                image.setAlignment(Image.ALIGN_CENTER);
                                cells1[j] =new PdfPCell(image);
                            }else {
                                cells1[j] =new PdfPCell(new Paragraph("暂无图片",FontChinese3));
                            }
                        }else {
                            cells1[j] =new PdfPCell(new Paragraph("暂无图片",FontChinese3));
                        }

                            cells1[j].setBorder(0);
                            cells1[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                            cells1[j].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                            cells1[j].setMinimumHeight(80);
                    }

                    for(int j=0;j<6;j++){
                            int n=6*i+j;
                            cells2[j] = new PdfPCell(new Paragraph("学号: "+admissionCard.get(n).getSid(),FontChinese1));
                            cells2[j].setBorder(0);
                            cells2[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                    }

                    for(int j=0;j<6;j++){
                            int n=6*i+j;
                            cells3[j] = new PdfPCell(new Paragraph("姓名:" + admissionCard.get(n).getName(), FontChinese1));
                            cells3[j].setBorder(0);
                            cells3[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                    }

                    for(int j=0;j<6;j++){
                            int n=6*i+j;
                            cells4[j] = new PdfPCell(new Paragraph("座位："+admissionCard.get(n).getSeat(),FontChinese1));
                            cells4[j].setBorder(0);
                            cells4[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                    }

                    for(int j=0;j<6;j++){
                            cells5[j] = new PdfPCell(new Paragraph("缺考      违纪", FontChinese1));
                            cells5[j].setBorder(0);
                            cells5[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                    }

                    listRow.add(row1);
                    listRow.add(row2);
                    listRow.add(row3);
                    listRow.add(row4);
                    listRow.add(row5);
                    document.add(table);

                }

                if (i==row-1){

                    PdfPTable table = new PdfPTable(6);
                    table.setWidthPercentage(100); // 宽度100%填充
                    table.setSpacingBefore(2f); // 前间距
                    table.setSpacingAfter(2f); // 后间距
                    List<PdfPRow> listRow = table.getRows();
                    float[] columnWidths = {1f, 1f, 1f,1f,1f,1f};
                    table.setWidths(columnWidths);


                    PdfPCell[] cells6=new PdfPCell[6];
                    PdfPCell[] cells7=new PdfPCell[6];
                    PdfPCell[] cells8=new PdfPCell[6];
                    PdfPCell[] cells9=new PdfPCell[6];
                    PdfPCell[] cells10=new PdfPCell[6];
                    PdfPRow row6=new PdfPRow(cells6);
                    PdfPRow row7=new PdfPRow(cells7);
                    PdfPRow row8=new PdfPRow(cells8);
                    PdfPRow row9=new PdfPRow(cells9);
                    PdfPRow row10=new PdfPRow(cells10);

                    for(int j=0;j<6;j++){
                        if (j<lastrow){
                            int n =6 * i + j;


                            cells6[j] = null; //单元格内容
                            String path =admissionCard.get(n).getPhoto();
                            logger.info("path:{}",path==null);
                            //测试时注释
                            if (path != null) {
                                logger.info("进入");
                                String photo = session.getServletContext().getRealPath(path);
                                File file=new File(photo);
                                if (file.exists()){
                                    Image image = Image.getInstance(photo);
                                    image.scaleToFit(90,210);
                                    image.setAlignment(Image.ALIGN_CENTER);
                                    cells6[j] = new PdfPCell(image);
                                }else {
                                    cells6[j] = new PdfPCell(new Paragraph("暂无图片",FontChinese3));
                                }
                            }else {
                                cells6[j] = new PdfPCell(new Paragraph("暂无图片",FontChinese3));
                            }

/*

                            logger.info("图片路径：{}", path);
                            Image image = Image.getInstance("C:\\Users\\SFX\\Desktop\\50fef9637bb71182.jpg");
                            image.scaleToFit(57, 133);
                            image.setAlignment(Image.ALIGN_CENTER);

                            cells6[j] = new PdfPCell(image);//单元格内容*/
                            cells6[j].setBorder(0);
                            cells6[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                            cells6[j].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                            cells6[j].setMinimumHeight(80);
                        }else {
                            cells6[j] = new PdfPCell();//单元格内容
                            cells6[j].setBorder(0);
                            cells6[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                            cells6[j].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                            cells6[j].setMinimumHeight(80);
                        }
                    }

                    for(int j=0;j<6;j++){
                        if (j<lastrow){
                            int n=6*i+j;
                            cells7[j] = new PdfPCell(new Paragraph("学号: "+admissionCard.get(n).getSid(),FontChinese1));
                            cells7[j].setBorder(0);
                            cells7[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                        }else {
                            cells7[j] = new PdfPCell();
                            cells7[j].setBorder(0);
                            cells7[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                        }

                    }

                    for(int j=0;j<6;j++){
                        if (j<lastrow){
                            int n=6*i+j;
                            cells8[j] = new PdfPCell(new Paragraph("姓名:" + admissionCard.get(n).getName(), FontChinese1));
                            cells8[j].setBorder(0);
                            cells8[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                        }else {
                            cells8[j] = new PdfPCell();
                            cells8[j].setBorder(0);
                            cells8[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                        }

                    }

                    for(int j=0;j<6;j++){
                        if (j<lastrow){
                            int n=6*i+j;
                            cells9[j] = new PdfPCell(new Paragraph("座位："+admissionCard.get(n).getSeat(),FontChinese1));
                            cells9[j].setBorder(0);
                            cells9[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                        }else {
                            cells9[j] = new PdfPCell();
                            cells9[j].setBorder(0);
                            cells9[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                        }

                    }

                    for(int j=0;j<6;j++){
                        if (j<lastrow){
                            cells10[j] = new PdfPCell(new Paragraph("缺考      违纪", FontChinese1));
                            cells10[j].setBorder(0);
                            cells10[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                        }else {
                            cells10[j] = new PdfPCell();
                            cells10[j].setBorder(0);
                            cells10[j].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                        }

                    }


                    listRow.add(row6);
                    listRow.add(row7);
                    listRow.add(row8);
                    listRow.add(row9);
                    listRow.add(row10);
                    document.add(table);

                }





            }




            document.close();
            writer.close();
            System.out.println("pdf exported success!");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //生成准考证
    public static void admissionTicket(AdmissionCard admissionCard,String realPath,String title,HttpSession session){

        Document document = new Document(PageSize.A4);
        try {
            //中文字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese, 30, Font.BOLD);
            Font FontChinese1 = new Font(bfChinese, 18, Font.BOLD);
            //写入器
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(realPath));
            document.open();

            Paragraph p1 = new Paragraph(title, FontChinese);
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setFont(FontChinese);
            document.add(p1);


            Paragraph ps = new Paragraph("准考证", FontChinese);
            ps.setAlignment(Element.ALIGN_CENTER);
            ps.setFont(FontChinese);
            document.add(ps);

            Paragraph p2= new Paragraph("学          号：  "+admissionCard.getSid(), FontChinese1);
            p2.setAlignment(Element.ALIGN_LEFT);
            p2.setFont(FontChinese1);


            Paragraph p3= new Paragraph("姓          名：  "+admissionCard.getName(), FontChinese1);
            p3.setAlignment(Element.ALIGN_LEFT);
            p3.setFont(FontChinese1);


            Paragraph p4= new Paragraph("性          别：  "+admissionCard.getSex(), FontChinese1);
            p4.setAlignment(Element.ALIGN_LEFT);
            p4.setFont(FontChinese1);


            Paragraph p5= new Paragraph("班          级：  "+admissionCard.getClazz(), FontChinese1);
            p5.setAlignment(Element.ALIGN_LEFT);
            p5.setFont(FontChinese1);




//=============================
            //表格
            PdfPTable table9 = new PdfPTable(2);
            table9.setWidthPercentage(100); // 宽度100%填充
            table9.setSpacingBefore(2f); // 前间距
            table9.setSpacingAfter(2f); // 后间距
            List<PdfPRow> listRow1 = table9.getRows();
            //设置列宽
            float[] columnWidths9 = {1f, 1f};
            table9.setWidths(columnWidths9);
            PdfPCell[] cells9 = new PdfPCell[2];
            PdfPRow row9 = new PdfPRow(cells9);
            //单元格
            cells9[0] = new PdfPCell(p2);//单元格内容
            cells9[0].setBorder(0);
            cells9[0].setVerticalAlignment(Element.ALIGN_LEFT);
            cells9[0].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居中
            cells9[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells9[0].setMinimumHeight(40);

            cells9[1] =null;//单元格内容
            String path =admissionCard.getPhoto();
            logger.info("path:{}",path==null);
            //测试时注释
            if (path != null) {
                logger.info("进入");
                String photo=session.getServletContext().getRealPath(path);
                File file=new File(photo);
                if (file.exists()){
                    Image image = Image.getInstance(photo);
                    image.scaleToFit(90,210);
                    image.setAlignment(Image.ALIGN_CENTER);
                    cells9[1] =new PdfPCell(image);
                }else {
                    cells9[1] =new PdfPCell(new Paragraph("暂无图片",FontChinese1));
                }
            }else {
                cells9[1] =new PdfPCell(new Paragraph("暂无图片",FontChinese1));
            }





            cells9[1].setBorder(0);
            cells9[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells9[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells9[1].setRowspan(4);
            cells9[1].setMinimumHeight(40);


            PdfPCell[] cells8 = new PdfPCell[2];
            PdfPRow row8 = new PdfPRow(cells8);
            cells8[0] = new PdfPCell(p3);
            cells8[0].setBorder(0);
            cells8[0].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居中
            cells8[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells8[0].setMinimumHeight(40);




            PdfPCell[] cells7 = new PdfPCell[2];
            PdfPRow row7 = new PdfPRow(cells7);
            cells7[0] = new PdfPCell( p4);
            cells7[0].setBorder(0);
            cells7[0].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居中
            cells7[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells7[0].setMinimumHeight(40);



            PdfPCell[] cells6 = new PdfPCell[2];
            PdfPRow row6 = new PdfPRow(cells6);
            cells6[0] = new PdfPCell(p5);
            cells6[0].setBorder(0);
            cells6[0].setHorizontalAlignment(Element.ALIGN_LEFT);//水平居中
            cells6[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells6[0].setMinimumHeight(40);




            listRow1.add(row9);
            listRow1.add(row8);
            listRow1.add(row7);
            listRow1.add(row6);
            document.add(table9);



            //========================================
            //表格
            PdfPTable table = new PdfPTable(4);

            table.setWidthPercentage(100); // 宽度100%填充
            table.setSpacingBefore(2f); // 前间距
            table.setSpacingAfter(2f); // 后间距

            List<PdfPRow> listRow = table.getRows();
            //设置列宽
            float[] columnWidths = {3f, 3f, 1f,1f};
            table.setWidths(columnWidths);

            PdfPCell[] cells1 = new PdfPCell[4];

            PdfPRow row1 = new PdfPRow(cells1);

            //单元格

            cells1[0] = new PdfPCell(new Paragraph("考试科目",FontChinese1));//单元格内容
            cells1[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells1[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells1[0].setMinimumHeight(40);



            cells1[1] = new PdfPCell(new Paragraph("考试时间",FontChinese1));
            cells1[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells1[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells1[1].setMinimumHeight(40);

            cells1[2] = new PdfPCell(new Paragraph("教室",FontChinese1));
            cells1[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells1[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells1[2].setMinimumHeight(40);

            cells1[3] = new PdfPCell(new Paragraph("座位号",FontChinese1));
            cells1[3].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells1[3].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells1[3].setMinimumHeight(40);
            listRow.add(row1);


            for(int i=0;i<admissionCard.getDetails().size();i++){
                PdfPCell[] cells2 = new PdfPCell[4];
                PdfPRow row2 = new PdfPRow(cells2);
                cells2[0] = new PdfPCell(new Paragraph(admissionCard.getDetails().get(i).getPname(),FontChinese1));
                cells2[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells2[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cells2[0].setMinimumHeight(40);


                cells2[1] = new PdfPCell(new Paragraph(admissionCard.getDetails().get(i).getTime(),FontChinese1));
                cells2[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells2[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cells2[1].setMinimumHeight(40);

                cells2[2] = new PdfPCell(new Paragraph(admissionCard.getDetails().get(i).getPlace(),FontChinese1));
                cells2[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells2[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cells2[2].setMinimumHeight(40);

                cells2[3] = new PdfPCell(new Paragraph(admissionCard.getDetails().get(i).getSeat(),FontChinese1));
                cells2[3].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells2[3].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
                cells2[3].setMinimumHeight(40);
                listRow.add(row2);
            }
            document.add(table);
            //========================================

            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //考试情况记载表
    public static void examDetails(String path){
        Document document = new Document(PageSize.A4);
        try {
            //中文字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese, 25, Font.BOLD);
            Font FontChinese1 = new Font(bfChinese, 15, Font.BOLD);
            Font FontChinese2 = new Font(bfChinese, 13, Font.BOLD);
            //写入器
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            Paragraph p1 = new Paragraph("考试情况记载表", FontChinese);
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setFont(FontChinese);
            document.add(p1);



            Paragraph p9 = new Paragraph("考场编号：            考试科目：            考试时间：            教室：            监考教师：            ", FontChinese2);
            p9.setAlignment(Element.ALIGN_LEFT);
            p9.setFont(FontChinese);
            document.add(p9);


            Paragraph p2= new Paragraph("缺考记载", FontChinese1);
            p2.setAlignment(Element.ALIGN_CENTER);
            p2.setFont(FontChinese);
            document.add(p2);


            //表格
            PdfPTable table = new PdfPTable(4);

            table.setWidthPercentage(100); // 宽度100%填充
            table.setSpacingBefore(2f); // 前间距
            table.setSpacingAfter(2f); // 后间距

            List<PdfPRow> listRow = table.getRows();
            //设置列宽
            float[] columnWidths = {1f, 1f,1f,1f};
            table.setWidths(columnWidths);

            PdfPCell[] cells1 = new PdfPCell[4];

            PdfPRow row1 = new PdfPRow(cells1);

            //单元格

            cells1[0] = new PdfPCell(new Paragraph("学号",FontChinese1));//单元格内容

            cells1[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells1[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells1[0].setMinimumHeight(25);

            cells1[1] = new PdfPCell(new Paragraph("姓名",FontChinese1));
            cells1[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells1[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells1[1].setMinimumHeight(25);

            cells1[2] = new PdfPCell(new Paragraph("学号",FontChinese1));
            cells1[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells1[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells1[2].setMinimumHeight(25);

            cells1[3] = new PdfPCell(new Paragraph("姓名",FontChinese1));
            cells1[3].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells1[3].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells1[3].setMinimumHeight(25);
            listRow.add(row1);


            for(int i=0;i<5;i++){
                PdfPCell[] cells2 = new PdfPCell[4];
                PdfPRow row2 = new PdfPRow(cells2);
                cells2[0] = new PdfPCell();
                cells2[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells2[0].setMinimumHeight(25);

                cells2[1] = new PdfPCell();
                cells2[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells2[1].setMinimumHeight(25);

                cells2[2] = new PdfPCell();
                cells2[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells2[2].setMinimumHeight(25);

                cells2[3] = new PdfPCell();
                cells2[3].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells2[3].setMinimumHeight(25);
                listRow.add(row2);
            }
            document.add(table);




//================================================================
            Paragraph p3= new Paragraph("违纪记载", FontChinese1);
            p3.setAlignment(Element.ALIGN_CENTER);
            p3.setFont(FontChinese);
            document.add(p3);

            //表格
            PdfPTable table1 = new PdfPTable(3);
            table1.setWidthPercentage(100); // 宽度100%填充
            table1.setSpacingBefore(2f); // 前间距
            table1.setSpacingAfter(2f); // 后间距
            List<PdfPRow> listRow1 = table1.getRows();
            //设置列宽
            float[] columnWidths1 = {1f, 1f, 4f};
            table1.setWidths(columnWidths1);

            PdfPCell[] cells12 = new PdfPCell[3];

            PdfPRow row12 = new PdfPRow(cells12);

            //单元格

            cells12[0] = new PdfPCell(new Paragraph("学号",FontChinese1));//单元格内容
            cells12[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells12[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells12[0].setMinimumHeight(60);



            cells12[1] = new PdfPCell(new Paragraph("姓名",FontChinese1));
            cells12[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells12[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells12[1].setMinimumHeight(60);

            cells12[2] = new PdfPCell(new Paragraph("违纪行为",FontChinese1));
            cells12[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells12[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells12[2].setMinimumHeight(60);

            listRow1.add(row12);
            for(int i=0;i<5;i++){
                PdfPCell[] cells3 = new PdfPCell[3];
                PdfPRow row3 = new PdfPRow(cells3);
                cells3[0] = new PdfPCell();
                cells3[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells3[0].setMinimumHeight(60);


                cells3[1] = new PdfPCell();
                cells3[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells3[1].setMinimumHeight(60);

                cells3[2] = new PdfPCell();
                cells3[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells3[2].setMinimumHeight(60);

                listRow1.add(row3);
            }

            document.add(table1);
            //========================================


            document.close();
            writer.close();
            System.out.println("pdf exported success!");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //考生座位对照表
    public static void contrast(Contrast contrasts,String path){
        logger.info("开始生成  考生座位对照表");
        Document document = new Document(PageSize.A4,10,10,0,0);
        try {
            //中文字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese, 20, Font.BOLD);
            Font FontChinese1 = new Font(bfChinese, 15, Font.BOLD);
            Font FontChinese2 = new Font(bfChinese, 13, Font.BOLD);
            //写入器
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            System.out.println("路径："+path);

            Paragraph p1 = new Paragraph("座位对照表", FontChinese);
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setFont(FontChinese);
            document.add(p1);

            Paragraph p2 = new Paragraph("学院："+contrasts.getCname()+"      年级："+contrasts.getGrade()+"      考试科目："+contrasts.getEname()+"      考场编号："+contrasts.getRid(), FontChinese2);
            p2.setAlignment(Element.ALIGN_CENTER);
            p2.setFont(FontChinese);
            document.add(p2);
/*
            Paragraph p10 = new Paragraph("      考场编号："+contrasts.getRid()+"      教室："+contrasts.getPlace(), FontChinese2);
            p10.setAlignment(Element.ALIGN_CENTER);
            p10.setFont(FontChinese);
            document.add(p10);*/

            Paragraph p3 = new Paragraph("教室："+contrasts.getPlace()+"      考试时间："+contrasts.getTime(), FontChinese2);
            p3.setAlignment(Element.ALIGN_CENTER);
            p3.setFont(FontChinese);
            document.add(p3);


            //表格
            PdfPTable table1 = new PdfPTable(3);
            table1.setWidthPercentage(100); // 宽度100%填充
            table1.setSpacingBefore(2f); // 前间距
            table1.setSpacingAfter(2f); // 后间距
            List<PdfPRow> listRow1 = table1.getRows();
            //设置列宽
            float[] columnWidths1 = {1f, 1f, 1f};
            table1.setWidths(columnWidths1);



            PdfPCell[] cells12 = new PdfPCell[3];
            PdfPRow row12 = new PdfPRow(cells12);
            //单元格

            cells12[0] = new PdfPCell(new Paragraph("学号",FontChinese1));//单元格内容
            cells12[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells12[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells12[0].setMinimumHeight(20);



            cells12[1] = new PdfPCell(new Paragraph("姓名",FontChinese1));
            cells12[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells12[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells12[1].setMinimumHeight(20);

            cells12[2] = new PdfPCell(new Paragraph("座位号",FontChinese1));
            cells12[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells12[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells12[2].setMinimumHeight(20);

            listRow1.add(row12);
            for(int i=0;i<contrasts.getDetails().size();i++){
                PdfPCell[] cells3 = new PdfPCell[3];
                PdfPRow row3 = new PdfPRow(cells3);
                cells3[0] = new PdfPCell(new Paragraph(""+contrasts.getDetails().get(i).getSid(),FontChinese2));
                cells3[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells3[0].setMinimumHeight(20);


                cells3[1] = new PdfPCell(new Paragraph(""+contrasts.getDetails().get(i).getSname(),FontChinese2));
                cells3[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells3[1].setMinimumHeight(20);

                cells3[2] = new PdfPCell(new Paragraph(""+contrasts.getDetails().get(i).getSeat(),FontChinese2));
                cells3[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells3[2].setMinimumHeight(20);
                listRow1.add(row3);
            }

            document.add(table1);
            document.close();
            writer.close();
            System.out.println("pdf exported success!");
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //考场安排表
    public static void roomArrangement(List<RoomArrangement> roomArrangements,String path){
        logger.info("开始生成考场安排表  考试时间"+roomArrangements.get(0).getTime());
        Document document = new Document(PageSize.A4,10,10,0,0);
        try {
            //中文字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font FontChinese = new Font(bfChinese, 20, Font.BOLD);
            Font FontChinese1 = new Font(bfChinese, 15, Font.BOLD);
            Font FontChinese2 = new Font(bfChinese, 13, Font.BOLD);
            //写入器
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            Paragraph p = new Paragraph(""+roomArrangements.get(0).getTitle(), FontChinese);
            p.setAlignment(Element.ALIGN_CENTER);

            p.setFont(FontChinese);
            document.add(p);


            Paragraph p1 = new Paragraph("考场安排表", FontChinese1);
            p1.setAlignment(Element.ALIGN_CENTER);
            p1.setSpacingAfter(1);
            p1.setFont(FontChinese);
            document.add(p1);


            Paragraph p3 = new Paragraph("考试时间："+roomArrangements.get(0).getTime(), FontChinese1);
            p3.setAlignment(Element.ALIGN_CENTER);
            p3.setSpacingAfter(3);
            p3.setFont(FontChinese);
            document.add(p3);


            //表格
            PdfPTable table1 = new PdfPTable(4);
            table1.setWidthPercentage(100); // 宽度100%填充
            table1.setSpacingBefore(2f); // 前间距
            table1.setSpacingAfter(2f); // 后间距
            List<PdfPRow> listRow1 = table1.getRows();
            //设置列宽
            float[] columnWidths1 = {1f, 1f, 1f,1f};
            table1.setWidths(columnWidths1);

            PdfPCell[] cells12 = new PdfPCell[4];

            PdfPRow row12 = new PdfPRow(cells12);

            //单元格

            cells12[0] = new PdfPCell(new Paragraph("考场编号",FontChinese1));//单元格内容
            cells12[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells12[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells12[0].setMinimumHeight(20);



            cells12[1] = new PdfPCell(new Paragraph("具体教室",FontChinese1));
            cells12[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells12[1].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells12[1].setMinimumHeight(20);

            cells12[2] = new PdfPCell(new Paragraph("考场人数",FontChinese1));
            cells12[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells12[2].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells12[2].setMinimumHeight(20);

            cells12[3] = new PdfPCell(new Paragraph("监考教师",FontChinese1));
            cells12[3].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
            cells12[3].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中
            cells12[3].setMinimumHeight(20);

            listRow1.add(row12);
            for(int i=0;i<roomArrangements.size();i++){
                PdfPCell[] cells3 = new PdfPCell[4];
                PdfPRow row3 = new PdfPRow(cells3);
                cells3[0] = new PdfPCell(new Paragraph(""+roomArrangements.get(i).getRid(),FontChinese2));
                cells3[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells3[0].setMinimumHeight(20);


                cells3[1] = new PdfPCell(new Paragraph(""+roomArrangements.get(i).getPlace(),FontChinese2));
                cells3[1].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells3[1].setMinimumHeight(20);

                cells3[2] = new PdfPCell(new Paragraph(""+roomArrangements.get(i).getNumber(),FontChinese2));
                cells3[2].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells3[2].setMinimumHeight(20);

                cells3[3] = new PdfPCell(new Paragraph(""+roomArrangements.get(i).getTeacher(),FontChinese2));
                cells3[3].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
                cells3[3].setMinimumHeight(20);

                listRow1.add(row3);
            }

            document.add(table1);
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("安排表生成");

    }



    /**
     * 将需要下载的生成为压缩包形式下载
     * @param session
     * @param response
     * @param paths
     * @throws Exception
     */
    public static void fileDownload(HttpSession session,HttpServletResponse response,List<String> paths,int length,String zip)throws Exception {
        //存放--服务器上zip文件的目录
        String directory = session.getServletContext().getRealPath(File.separator+"cachefile");

        File directoryFile=new File(directory);
        if(!directoryFile.isDirectory() && !directoryFile.exists()){
            directoryFile.mkdirs();
        }
      //  String zipFileName = UUID.randomUUID().toString().replaceAll("-", "")+".zip";
        String zipFileName =zip+".zip";
      //  logger.info("生成文件名称UUID：{}",zipFileName);
        String strZipPath = directory+File.separator+zipFileName;

        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        File zipFile = new File(strZipPath);
        try{
            //构造最终压缩包的输出流
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            String spl=null;
            //判断当前系统
            Properties prop = System.getProperties();
            String os = prop.getProperty("os.name");
            if (os != null && os.toLowerCase().indexOf("linux") > -1) {
                //linux系统
                spl="/";
               // logger.info("linux系统");
            } else {
                //windows系统
                spl="\\\\";
              //  logger.info("windows系统");
            }

            for (int i = 0; i<paths.size() ;i++){
              //  logger.info("工具类里面获取的路径=={}",paths.get(i));
                String realFilePath = session.getServletContext().getRealPath(paths.get(i));
             //   logger.info("realFilePath:{}",realFilePath);
                String[] filename1=paths.get(i).split(spl);
                String realFileName="" ;//java.net.URLDecoder.decode(names[i],"UTF-8");
                //----------------------------
              //  logger.info("{}",length>3);
                if (length>3){
                    for (int z=3;z<filename1.length;z++){
                        if (z==filename1.length-1){
                            realFileName=realFileName+filename1[z];
                        }else {
                            realFileName=realFileName+filename1[z]+File.separator;
                        }
                   //     logger.info("{}",realFileName);
                    }

                }else{
                    realFileName=filename1[length];
                }
                //----------------------------
            //    logger.info("realFileName：{}",realFileName);
                File file = new File(realFilePath);
                //TODO:未对文件不存在时进行操作，后期优化。
                if(file.exists())
                {
                    zipSource = new FileInputStream(file);//将需要压缩的文件格式化为输入流

                    ZipEntry zipEntry = new ZipEntry(realFileName);//在压缩目录中文件的名字
                    zipStream.putNextEntry(zipEntry);//定位该压缩条目位置，开始写入文件到压缩包中
                    bufferStream = new BufferedInputStream(zipSource, 1024 * 10);
                    int read = 0;
                    byte[] buf = new byte[1024 * 10];
                    while((read = bufferStream.read(buf, 0, 1024 * 10)) != -1)
                    {
                        zipStream.write(buf, 0, read);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if(null != bufferStream) bufferStream.close();
                if(null != zipStream){
                    zipStream.flush();
                    zipStream.close();
                }
                if(null != zipSource) zipSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //判断系统压缩文件是否存在：true-把该压缩文件通过流输出给客户端后删除该压缩文件  false-未处理
        if(zipFile.exists()){
            FileUtil.downFile(response,zipFileName,strZipPath);
            zipFile.delete();
        }


    }







}
