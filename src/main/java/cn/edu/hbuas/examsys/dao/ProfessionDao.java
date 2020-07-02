package cn.edu.hbuas.examsys.dao;

import cn.edu.hbuas.examsys.mybatis.mapper.ProfessionMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 牛传喜
 * 专业表的操作
 */

@Repository
public class ProfessionDao {
    @Autowired
    private ProfessionMapper professionMapper;

    /**
     * 根据主键查找专业编号
     * @param pid
     * @return
     */
    public Profession selectByID(int pid){
        return professionMapper.selectByPrimaryKey(pid);
    }
}
