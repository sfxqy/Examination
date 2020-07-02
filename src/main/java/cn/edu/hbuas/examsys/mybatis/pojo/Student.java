package cn.edu.hbuas.examsys.mybatis.pojo;

public class Student extends StudentKey {
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


    public Student(String sid, Integer tid, String sid1, Integer tid1, String sname, String sex, String grade, String clazz, Integer pid, Integer cid, String idcard, String photo, Integer state) {
        super(sid, tid);
        this.sid = sid1;
        this.tid = tid1;
        this.sname = sname;
        this.sex = sex;
        this.grade = grade;
        this.clazz = clazz;
        this.pid = pid;
        this.cid = cid;
        this.idcard = idcard;
        this.photo = photo;
        this.state = state;
    }

    public Student(String sid, Integer tid, String sname, String sex, String grade, String clazz, Integer pid, Integer cid, String idcard, String photo, Integer state) {
        super(sid, tid);
        this.sname = sname;
        this.sex = sex;
        this.grade = grade;
        this.clazz = clazz;
        this.pid = pid;
        this.cid = cid;
        this.idcard = idcard;
        this.photo = photo;
        this.state = state;
    }

    public Student() {
        super();
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
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
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String getSid() {
        return sid;
    }

    @Override
    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public Integer getTid() {
        return tid;
    }

    @Override
    public void setTid(Integer tid) {
        this.tid = tid;
    }
}