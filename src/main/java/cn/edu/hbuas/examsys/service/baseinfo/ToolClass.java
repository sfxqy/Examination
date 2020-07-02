package cn.edu.hbuas.examsys.service.baseinfo;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * author:yf
 * 学院信息管理

 */
public class ToolClass{

    // 根据身份证号获取年龄
    public int IdNOToAge(String IdNO){
        int leh = IdNO.length();
        String dates="";
        if (leh == 18) {
            int se = Integer.valueOf(IdNO.substring(leh - 1)) % 2;
            dates = IdNO.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year=df.format(new Date());
            int u=Integer.parseInt(year)-Integer.parseInt(dates);
            return u;
        }else{
            dates = IdNO.substring(0, 6);
            return Integer.parseInt(dates);
        }

    }
    public String getIdcard(String idcard){
        String card=idcard.substring(0,6);
        return card;
    }

}