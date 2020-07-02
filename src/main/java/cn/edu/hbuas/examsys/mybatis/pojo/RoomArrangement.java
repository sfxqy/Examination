package cn.edu.hbuas.examsys.mybatis.pojo;

/**
 * @author SFX
 * 考场安排实体类
 */
public class RoomArrangement {

    //考试名称
    private String title;

    //考试时间
    private String time;

    //考场编号
    private Integer rid;

    //具体教室
    private String place;

    //考场人数
    private Integer number;

    //监考教师
    private String teacher;

    private String college;


    private String name;

    public RoomArrangement() {
    }

    public RoomArrangement(String title, String time, Integer rid, String place, Integer number, String teacher, String college, String name) {
        this.title = title;
        this.time = time;
        this.rid = rid;
        this.place = place;
        this.number = number;
        this.teacher = teacher;
        this.college = college;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
