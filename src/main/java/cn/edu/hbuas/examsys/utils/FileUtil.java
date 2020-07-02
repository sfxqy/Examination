package cn.edu.hbuas.examsys.utils;



import cn.edu.hbuas.examsys.mybatis.pojo.*;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author SFX
 *文件操作
 */

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);


    /**
     * 获取文件名称
     * @return
     */
    public static String getFileName() {
        return null;
    }


    /**
     * 检查文件的正确性
     * @param file
     * @throws Exception
     */
    public static void checkFile(MultipartFile file) throws Exception {
        logger.info("检查Excel文件");
        if (file == null) {
            logger.info("Excel文件不存在");
            throw new BusinessException("文件不存在");
        }
        String filename = file.getOriginalFilename();
        if (!filename.endsWith(".xls") && !filename.endsWith(".xlsx")) {
            logger.info("不是excel文件，或不支持该文件格式");
            throw new BusinessException("不是Excel文件，或不支持该文件格式");
        }
    }





    /**
     * 检查文件合法性，并获取文件保存路径
     * @param session
     * @param files
     * @return
     * @throws Exception
     */
    public static List<Student> getFileName(HttpSession session, MultipartFile[] files) throws Exception {
        List<String> imgs = Arrays.asList("jpg", "png", "jpeg", "gif", "bmp");
        List<Student> filenames = new ArrayList<Student>();
        logger.info("文件组数量：{}",files.length);
        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getOriginalFilename();
            logger.info("上传后的文件名称：{}",filename);
            String last=filename.substring(filename.lastIndexOf(".")+1);
            String frontname=filename.substring(0,filename.lastIndexOf("."));
            logger.info("文件名称{}",frontname);
            if (!imgs.contains(last)) {
                throw new BusinessException("无法上传" + filename + "，仅支持jpg、png、jpeg、gif、bmp格式的文件");
            }
            String path = File.separator+"photo" +File.separator+ frontname+ File.separator + filename;
            Student student = new Student();
            student.setSid(frontname);
            student.setPhoto(path);
            filenames.add(student);

        }
        return filenames;
    }



    /**
     * 保存照片到服务器
     * @param session
     * @param files
     */
    public static void savePhoto(HttpSession session, MultipartFile[] files) throws Exception {
        ServletContext servletContext = session.getServletContext();
        for (int i = 0; i < files.length; i++) {
            String filename = files[i].getOriginalFilename();
            String path=File.separator+"photo"+File.separator +filename.substring(0,filename.lastIndexOf("."));
            String path1 = servletContext.getRealPath(path);
            File file = new File(path1, filename);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            } else {
                deleteFile(session, path + filename);
            }
            files[i].transferTo(new File(path1 + File.separator + filename));
            logger.info("文件保存地址:{}",path1);
        }
    }


    /**
     * 删除服务器中相应的文件
     * @param session
     * @param path
     */
    public static void deleteFile(HttpSession session, String path) {
        if (path == null) {
            return;
        }
        String realURL = session.getServletContext().getRealPath(path);
        File file = new File(realURL);
        if (file.exists()) {
            file.delete();
        }

    }


    /**
     *下载图片
     * @param response
     * @param session
     * @param filename
     * @throws IOException
     */
    public static void downloadPhoto(HttpServletResponse response, HttpSession session, String filename) throws IOException {
        String realPath = session.getServletContext().getRealPath(filename);
        File file = new File(realPath);
        if (file.exists()) {
            String[] filename1=filename.split("/");
            for(int i=0;i<filename1.length;i++){
                logger.info("{}",filename1[i]);
            }
            response.setContentType("application/force-download");// 设置强制下载不打开            
            response.addHeader("Content-Disposition", "attachment;fileName=" + filename1[3]);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream outputStream = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {

            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * 将需要下载的生成为压缩包形式下载
     * @param session
     * @param response
     * @param paths
     * @throws Exception
     */
    public static void imgDownload(HttpSession session,HttpServletResponse response,List<String> paths)throws Exception {
        //存放--服务器上zip文件的目录
        String directory = session.getServletContext().getRealPath(File.separator+"cachefile");

        File directoryFile=new File(directory);
        if(!directoryFile.isDirectory() && !directoryFile.exists()){
            directoryFile.mkdirs();
        }

        String zipFileName = UUID.randomUUID().toString().replaceAll("-", "")+".zip";
        logger.info("生成文件名称UUID：{}",zipFileName);
        //String zipFileName = formatter.format(new Date())+".zip";
        String strZipPath = directory+File.separator+zipFileName;

        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        File zipFile = new File(strZipPath);
        try{
            //构造最终压缩包的输出流
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i<paths.size() ;i++){
                logger.info("工具类里面获取的路径=={}",paths.get(i));
                String realFilePath = session.getServletContext().getRealPath(paths.get(i));
                logger.info("realFilePath:{}",realFilePath);
                String[] filename1=paths.get(i).split("/");
                String realFileName = filename1[3];//java.net.URLDecoder.decode(names[i],"UTF-8");
                logger.info("realFileName：{}",realFileName);
               // String realFilePath =directory+File.separator+paths.get(i);

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
            downFile(response,zipFileName,strZipPath);
            zipFile.delete();
        }
    }


    /**
     * 下载照片
     * @param response
     * @param filename
     * @param path
     * @throws Exception
     */
    public static void downFile(HttpServletResponse response,String filename,String path )throws Exception{
        if (filename != null) {
            FileInputStream is = null;
            BufferedInputStream bs = null;
            OutputStream os = null;
            try {
                File file = new File(path);
                if (file.exists()) {
                    //设置Headers

                    response.setHeader("Content-Type","application/octet-stream");
                    //设置下载的文件的名称-该方式已解决中文乱码问题
                    response.setHeader("Content-Disposition","attachment;filename=" +  new String( filename.getBytes("gb2312"), "ISO8859-1" ));
                    is = new FileInputStream(file);
                    bs =new BufferedInputStream(is);
                    os = response.getOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while((len = bs.read(buffer)) != -1){
                        os.write(buffer,0,len);
                    }
                }else{
                    throw new BusinessException("下载的文件资源不存在");
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }finally {
                try{
                    if(is != null){
                        is.close();
                    }
                    if( bs != null ){
                        bs.close();
                    }
                    if( os != null){
                        os.flush();
                        os.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 公用读取excel表工具
     * @param file 传入的excel文件
     * @param cids 院别编号
     * @param propertys 需要解析的excel表对应实体类中的属性名称集合
     * @param obj
     * @return
     * @throws Exception
     */
    public static <T extends ReadFileFather> List  readCommonalityExcel(MultipartFile file, Integer cids,List<String> propertys,T obj) throws Exception {
        logger.info("读取Excel文件");
        checkFile(file);
        Workbook workbook = null;
        String filename = file.getOriginalFilename();
        InputStream input = file.getInputStream();
        if (filename.endsWith("xls")) {
            workbook = new HSSFWorkbook(input);
        } else {
            workbook = new XSSFWorkbook(input);
        }
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            logger.info("Excel文件为空");
            throw new BusinessException("文件为空");
        }
        logger.info("长度{}", sheet.getLastRowNum());
        List list=new ArrayList();
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            logger.info("sheet.getLastRowNum():{}",sheet.getLastRowNum());
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }

        Object object = null;
        if(r==0){
            object = obj;
        }else{
            object =   obj.getClass().newInstance();
        }

        Class c=object.getClass();

            for (int i=0;i<propertys.size();i++) {
                logger.info("i:{},propertys.size():{}",i,propertys.size());
                Object value = getCellValue(row, i);
                Field field=c.getDeclaredField(propertys.get(i));

                field.setAccessible(true);

         //       logger("是否为cid：{}  {}  {}  {}",(propertys.get(i).equals("cid")),(cids!=0),(cids!=-1),(cids!=value));
                if (propertys.get(i).equals("cid")){
                    String cid=value.toString();
                    if (cids!=0&&cids!=-1&&!cid.equals(cids.toString())){
                        logger.info("禁止上传非本院相关学生信息");
                        throw new BusinessException("禁止上传非本院相关学生信息");
                    }
                }
                try {
                    logger.info("参数：{}  数据类型：{},值：{}",propertys.get(i),field.getGenericType().toString(),value);
                    if (field.getGenericType().toString().equals("class java.lang.String")){
                        if (value instanceof String);
                        else{
                            value=value.toString();
                        }
                        field.set(object,(String)value);
                    }
                    if (field.getGenericType().toString().equals("class java.lang.Integer")){
                        if(value instanceof String){
                            value=Integer.parseInt((String) value);
                        }
                        field.set(object,(Integer)value);
                    }
                    if (field.getGenericType().toString().equals("class java.util.Date"))
                        field.set(object,(Date)value);
                }catch (Exception e){
                    logger.info("数据格式不正确，请核对后上传 参数：属性名称：{}，行数：{}",propertys.get(i),r);
                    throw new BusinessException("数据格式不正确，请核对后上传");
                }
            }
            list.add(object);
        }
        return list;
    }


    /**
     * 获取单元格中的数据
     * @param row
     * @param column
     * @return
     * @throws Exception
     */
    public static Object getCellValue(Row row,Integer column)throws Exception {
        Object value=null;
        switch (row.getCell(column).getCellType()) {
            case STRING:
                value=row.getCell(column).getStringCellValue();
                logger.info("该行为字符串类类型");
                break;
            case NUMERIC:
               logger.info("该行为数值类型");
                if (HSSFDateUtil.isCellDateFormatted(row.getCell(column))) {// 判断是否为日期类型
                    Date date = row.getCell(column).getDateCellValue();
                    DateFormat formater=null;
                    String pat=null;
                    if("m/d/yy".equals(row.getCell(column).getCellStyle().getDataFormatString())){
                        pat="yyyy-MM-dd";
                        formater = new SimpleDateFormat(pat);
                    }if ("m/d/yy h:mm".equals(row.getCell(column).getCellStyle().getDataFormatString())){
                        pat="yyyy-MM-dd HH:mm";
                        formater = new SimpleDateFormat(pat);
                    }
                    String date1 = formater.format(date);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pat);
                    value=simpleDateFormat.parse(date1);
                    logger.info("value:{}",value);
                }else{
                    value = (int)row.getCell(column).getNumericCellValue();
                }
                break;
            case BOOLEAN:
                logger.info("该行为布尔类型");
                value = row.getCell(column).getBooleanCellValue();
                break;
            case BLANK:
                throw new BusinessException("表信息不完整，请补充完整后上传");
            case ERROR:
                throw new BusinessException("表读取异常，请重新再试");
        }
        if (value==null)
            throw new BusinessException("表信息不完整，请补充完整");
        return value;
    }





    //生成excel表格
    public static void getHSSFWorkbook( String []title,/*String [][]values*/List<AroundTest> aroundTests,HttpServletResponse response,String fileName){
        XSSFWorkbook wb=new XSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet("first");

        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
        XSSFRow row = sheet.createRow(0);

        // 第四步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        //  style.setAlignment(); // 创建一个居中格式

        //声明列对象
        XSSFCell cell = null;

        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }



        int i=1;
        for (AroundTest a:aroundTests){
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(a.getRid());
            row.createCell(1).setCellValue(a.getColleage());
            row.createCell(2).setCellValue(a.getClazz());
            row.createCell(3).setCellValue(a.getName());
            row.createCell(4).setCellValue(a.getPlace());
            row.createCell(5).setCellValue(a.getTime());
            row.createCell(6).setCellValue(a.getTeacher());
            row.createCell(7).setCellValue(a.getWorknumber());

            i++;
        }

        try {
            setResponseHeader( response,  fileName);
            OutputStream outputStream=response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }




    public static void getHSSFWorkbook1( String []title,/*String [][]values*/List<RoomArrangement> aroundTests,HttpServletResponse response,String fileName){
        XSSFWorkbook wb=new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("first");
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle style = wb.createCellStyle();
        XSSFCell cell = null;
        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        int i=1;
        for (RoomArrangement a:aroundTests){
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(a.getRid());
            row.createCell(1).setCellValue(a.getCollege());
            row.createCell(2).setCellValue(a.getName());
            row.createCell(3).setCellValue(a.getPlace());
            row.createCell(4).setCellValue(a.getNumber());
            row.createCell(5).setCellValue(a.getTeacher());
            i++;
        }

        try {
            setResponseHeader( response,  fileName);
            OutputStream outputStream=response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }










}



