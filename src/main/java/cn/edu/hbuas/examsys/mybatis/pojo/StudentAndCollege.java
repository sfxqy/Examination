package cn.edu.hbuas.examsys.mybatis.pojo;

public class StudentAndCollege {
    private String sid;
    private Integer tid;
    private String sname;
    private String sex;
    private String grade;



    private String clazz;
    private Integer pid;
    private Integer cid;
    private String idcard;
    private String photo;

    private Integer state;




    private String college;
    private String pname;
    public StudentAndCollege(String sid,Integer tid, String sname, String sex, String grade, String clazz,String college, Integer pid, String pname, String idcard, String photo, Integer state,Integer cid) {
        this.sid = sid;
        this.tid=tid;
        this.sname = sname;
        this.sex = sex;
        this.grade = grade;
        this.clazz = clazz;
        this.college = college;
        this.pid = pid;
        this.pname = pname;
        this.idcard = idcard;
        this.photo = photo;
        this.state = state;
        this.cid = cid;

    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}