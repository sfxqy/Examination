package cn.edu.hbuas.examsys.mybatis.pojo;

public class AroundTest {

    private Integer rid;

    private Integer cid;

    private String colleage;

    private String clazz;

    private String name;

    private String place;

    private String time;

    private String teacher;

    private String worknumber;


    public AroundTest() {
    }

    public AroundTest(Integer rid, Integer cid, String colleage, String clazz, String name, String place, String time, String teacher, String worknumber) {
        this.rid = rid;
        this.cid = cid;
        this.colleage = colleage;
        this.clazz = clazz;
        this.name = name;
        this.place = place;
        this.time = time;
        this.teacher = teacher;
        this.worknumber = worknumber;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getColleage() {
        return colleage;
    }

    public void setColleage(String colleage) {
        this.colleage = colleage;
    }

    public String getWorknumber() {
        return worknumber;
    }

    public void setWorknumber(String worknumber) {
        this.worknumber = worknumber;
    }


    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


}
