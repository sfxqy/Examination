package cn.edu.hbuas.examsys.mybatis.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 从数据查询出来的短信封装对象
 * @author panboyuan
 */
public class NoteDTO {
    //考试地点
    private String place;
    //监考教室 由- 分隔
    private String teacher;
    //考试的具体日期
    private Date testtime;
    //开始时间
    private Date begin;
    private Date end;
    //考试科目的名称
    private String name;
    //教师工号 -分隔
    private String worknumber;

    public String getWorknumber() {
        return worknumber;
    }

    public void setWorknumber(String worknumber) {
        this.worknumber = worknumber;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Date getTesttime() {
        return testtime;
    }

    public void setTesttime(Date testtime) {
        this.testtime = testtime;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NoteDTO{" +
                "place='" + place + '\'' +
                ", teacher='" + teacher + '\'' +
                ", testtime=" + testtime +
                ", begin=" + begin +
                ", end=" + end +
                ", name='" + name + '\'' +
                ", worknumber='" + worknumber + '\'' +
                '}';
    }
}
