package cn.edu.hbuas.examsys.utils;

import cn.edu.hbuas.examsys.springmvc.exception.CheckException;


/**
 * @author SFX
 */
public class CheckUtil {



    public static void notNull(Object obj,String msg){
        if (obj==null){
            fail(msg);
        }
    }



    /**
     *
     * @param msg 自定义信息
     */
    public static void fail(String msg){
        throw  new CheckException(msg);
    }








}
