package cn.edu.hbuas.examsys.model;

import cn.edu.hbuas.examsys.mybatis.pojo.Student;

/**
 * @author 牛传喜
 */
public class Examinee extends Student {
    String pname;
    Integer eid;

    public String getPname() {
        return pname;
    }

    public Integer getEid() {
        return eid;
    }

    public void setEid(Integer eid) {
        this.eid = eid;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
