package cn.edu.hbuas.examsys.service;

import cn.edu.hbuas.examsys.model.User;
import cn.edu.hbuas.examsys.mybatis.mapper.CustomerMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Customer;
import cn.edu.hbuas.examsys.mybatis.pojo.CustomerExample;
import cn.edu.hbuas.examsys.utils.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author SFX
 */

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private CustomerMapper customerMapper;


    /**
     * @param session
     * @param customer
     */
    public Customer Login(HttpSession session, Customer customer){
        logger.info("[管理员登录中 账号:{}  密码:xxxxxx]");
        CheckUtil.notNull(customer.getAccount(),"账号不能为空");
        CheckUtil.notNull(customer.getPassword(),"密码不能为空");
        CustomerExample customerExample=new CustomerExample();
        customerExample.createCriteria().andAccountEqualTo(customer.getAccount());
        List<Customer> customerList=customerMapper.selectByExample(customerExample);
        if (customerList.size()==0){
            logger.info("[该账号不存在]");
            CheckUtil.fail("该账号不存在");
        }
        Customer customer1=customerList.get(0);
        if (!customer1.getPassword().equals(customer.getPassword())){
            logger.info("[密码错误]");
            CheckUtil.fail("密码错误");
        }
        logger.info("[登录成功 账号：{}]",customer1.getAccount());
        logger.info("{}  {}   {}   ",customer1.getId(),customer1.getAccount(),customer1.getCid());
        User user=new User(customer1.getId(),customer1.getAccount(),customer1.getCid());
        session.setAttribute("user",user);
        customer1.setPassword(null);//密码置空
        return customer1;
    }
}
