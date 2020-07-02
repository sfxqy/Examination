package cn.edu.hbuas.examsys.mybatis.pojo;

/**
 * 用来存放老师的监考安排
 * @author panboyuan
 */
public class TeacherArrange {
    //教室
    private String roomPlace;
    //考试名称
    private String examName;
    //开始时间
    private String begin;
    //结束时间
    private String end;
    //考试日期
    private String examTime;


    @Override
    public String toString() {
        return "TeacherArrange{" +
                "roomPlace='" + roomPlace + '\'' +
                ", examName='" + examName + '\'' +
                ", begin='" + begin + '\'' +
                ", end='" + end + '\'' +
                ", examTime='" + examTime + '\'' +
                '}';
    }

    public String getRoomPlace() {
        return roomPlace;
    }

    public void setRoomPlace(String roomPlace) {
        this.roomPlace = roomPlace;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }
}
