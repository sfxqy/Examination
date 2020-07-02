package cn.edu.hbuas.examsys.mybatis.pojo;

import java.util.List;

/**
 * 短信发送的封装类
 * @author panboyuan
 */
public class Note {
    //教师工号
    private String workNumber;
    //教师名字
    private String name;
    //教室电话
    private String phone;

    //存放一个老师有几场监考
    private List<TeacherArrange> teacherArrangeList;

    @Override
    public String toString() {
        return "Note{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", teacherArrangeList=" + teacherArrangeList +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<TeacherArrange> getTeacherArrangeList() {
        return teacherArrangeList;
    }

    public void setTeacherArrangeList(List<TeacherArrange> teacherArrangeList) {
        this.teacherArrangeList = teacherArrangeList;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }
}
