package cn.edu.hbuas.examsys.model;

/**
 * 常量类
 * @author panboyuan
 */
public class Constants {
    public Constants() {
    }

    /**
     * 常规考试
     */
    public static final String REGULAR_EXAMINATION = "0";

    /**
     * 补考
     */
    public static final String RETEST_EXAMINATION = "1";

    /**
     * 学位考试
     */
    public static final String DEGREE_EXAMINATION = "2";

    /**
     * 考试任务编辑中
     */
    public static final int PLAN_EDIT = 1;
    /**
     * 考试任务编排完成
     */
    public static final int PLAN_SUCCESS = 2;

    /**
     * 考试进行中
     */
    public static final int PLAN_PROCEED = 3;

    /**
     * 考试任务完成
     */
    public static final int PLAN_COMPLETE = 4;

}
