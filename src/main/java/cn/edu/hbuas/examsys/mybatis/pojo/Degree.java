package cn.edu.hbuas.examsys.mybatis.pojo;

public class Degree extends DegreeKey {
    private String name;

    private String sex;

    private String level;

    private String clazz;

    private Integer pid;

    private Integer cid;

    private String grade;

    private String phone;

    public Degree(Integer sid, Integer tid, String name, String sex, String level, String clazz, Integer pid, Integer cid, String grade, String phone) {
        super(sid, tid);
        this.name = name;
        this.sex = sex;
        this.level = level;
        this.clazz = clazz;
        this.pid = pid;
        this.cid = cid;
        this.grade = grade;
        this.phone = phone;
    }

    public Degree() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}