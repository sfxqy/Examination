package cn.edu.hbuas.examsys.mybatis.pojo;

/**
 * @author SFX
 * 门贴实体类
 */
public class DoorPost {

    //考试标题
    private String title;

    //院别编号
    private Integer cid;

    //院别名称
    private String cname;

    //年级
    private String grade;

    //班级
    private String clazz;

    //考试科目
    private String name;

    //考试时间
    private String time;

    //考试地点
    private String place;

    //考场编号
    private Integer rid;

    //监考教师
    private String teacher;

    public DoorPost() {
    }

    public DoorPost(String title, Integer cid, String cname, String grade, String clazz, String name, String time, String place, Integer rid, String teacher) {
        this.title = title;
        this.cid = cid;
        this.cname = cname;
        this.grade = grade;
        this.clazz = clazz;
        this.name = name;
        this.time = time;
        this.place = place;
        this.rid = rid;
        this.teacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
