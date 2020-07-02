package cn.edu.hbuas.examsys.mybatis.pojo;

public class Teacher extends ReadFileFather{
    private Integer tid;

    private String number;

    private String tname;

    private String sex;

    private String wages;

    private String phone;

    private Integer cid;

    private Integer rid;

    private Integer times;

    public Teacher(Integer tid, String number, String tname, String sex, String wages, String phone, Integer cid, Integer rid, Integer times) {
        this.tid = tid;
        this.number = number;
        this.tname = tname;
        this.sex = sex;
        this.wages = wages;
        this.phone = phone;
        this.cid = cid;
        this.rid = rid;
        this.times = times;
    }

    public Teacher() {
        super();
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getWages() {
        return wages;
    }

    public void setWages(String wages) {
        this.wages = wages == null ? null : wages.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}