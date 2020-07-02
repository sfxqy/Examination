package cn.edu.hbuas.examsys.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 牛传喜
 * 关于时间和日期的工具类
 */
public class DateTimeUtil {


    /**
     * 获取当前的年月日
     * @return 当前日期的字符串
     */
    public static String getNowDate(){
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(date);
    }


    public static List<Date> getTimeList() throws Exception{
        List<Date> dateList = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("HH:mm");
        Date date = format.parse("09:00");
        dateList.add(date);
        date = format.parse("11:30");
        dateList.add(date);
        date = format.parse("14:00");
        dateList.add(date);
        date = format.parse("16:30");
        dateList.add(date);
        return dateList;
    }

    public static void show(){
        DateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date date = format.parse("09:00");
            System.out.println(format.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * 以年月日的形式显示特定的日期
     * @param date 需要显示的日期
     * @return 字符串
     */
    public static String showDate(Date date){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        return format.format(date);
    }

    /**
     * 以时分秒的格式转换日期
     * @param date
     * @return
     */
    public static String showTime(Date date){
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }


    /**
     * 按照天数在原有的日期上加上传递的天数
     * @param date 基础日期
     * @param i 需要增加的天数
     * @return
     */
    public static Date dateAddDay(Date date, int i){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,i);
        Date stop = calendar.getTime();
        return stop;
    }
}
