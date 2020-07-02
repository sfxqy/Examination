package cn.edu.hbuas.examsys.springmvc.exception;

/**
 * @author 牛传喜
 * 持久层异常抛出：可以触发事务回滚
 */
public class RepositoryException extends RuntimeException{
    private String repMsg;

    public String getRepMsg() {
        return repMsg;
    }

    public void setRepMsg(String repMsg) {
        this.repMsg = repMsg;
    }

    public RepositoryException(){
        super();
    }

    public RepositoryException(String msg){
        super(msg);
    }

    public RepositoryException(String msg,String repMsg){
        super(msg);
        this.repMsg = repMsg;
    }
}
