package cn.edu.hbuas.examsys.mybatis.pojo;

public class Examcourse extends ReadFileFather{
    private Integer eid;

    private Integer tid;

    private Integer cid;

    private Integer pid;

    private String grade;

    private String clazz;

    private String name;

    private Integer number;

    private Integer resetNumber;

    private Integer total;

    private String testForm;

    private Integer priority;

    private Integer accomplish;

    public Examcourse(Integer eid, Integer tid, Integer cid, Integer pid, String grade, String clazz, String name, Integer number, Integer resetNumber, Integer total, String testForm, Integer priority, Integer accomplish) {
        this.eid = eid;
        this.tid = tid;
        this.cid = cid;
        this.pid = pid;
        this.grade = grade;
        this.clazz = clazz;
        this.name = name;
        this.number = number;
        this.resetNumber = resetNumber;
        this.total = total;
        this.testForm = testForm;
        this.priority = priority;
        this.accomplish = accomplish;
    }

    public Examcourse() {
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getResetNumber() {
        return resetNumber;
    }

    public void setResetNumber(Integer resetNumber) {
        this.resetNumber = resetNumber;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getTestForm() {
        return testForm;
    }

    public void setTestForm(String testForm) {
        this.testForm = testForm == null ? null : testForm.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getAccomplish() {
        return accomplish;
    }

    public void setAccomplish(Integer accomplish) {
        this.accomplish = accomplish;
    }
}