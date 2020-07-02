package cn.edu.hbuas.examsys.service.baseinfo;

import cn.edu.hbuas.examsys.mybatis.mapper.CustomerMapper;
import cn.edu.hbuas.examsys.mybatis.pojo.Customer;
import cn.edu.hbuas.examsys.mybatis.pojo.CustomerExample;
import cn.edu.hbuas.examsys.mybatis.pojo.Teacher;
import cn.edu.hbuas.examsys.springmvc.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 牛传喜
 */

/**
 * 用户信息管理
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 添加用户信息
     * @param customer
     */
    public void addCustomer(Customer customer) throws Exception
    {
        CustomerExample customerExample=new CustomerExample();
        CustomerExample.Criteria criteria=customerExample.createCriteria();
        criteria.andAccountEqualTo(customer.getAccount());

        customerExample.or(criteria);
        List<Customer> list=customerMapper.selectByExample(customerExample);
        if(list.size()>0)
        {
            throw new BusinessException("该用户名已存在!");
        }
        customerMapper.insertSelective(customer);
    }

    /**
     * 删除用户信息
     * @param customer
     */
    public void deleteCustomer(Customer customer) throws Exception
    {
        Customer customer1=customerMapper.selectByPrimaryKey(customer.getId());
        if(customer1==null)
        {
            throw new BusinessException("不存在该用户");
        }
        customerMapper.deleteByPrimaryKey(customer.getId());
    }

    /**
     * 修改用户信息
     * @param customer
     */
    public void updateCustomer(Customer customer) throws Exception
    {
        Customer customer1=customerMapper.selectByPrimaryKey(customer.getId());
        if(customer1==null)
        {
            throw new BusinessException("不存在该用户");
        }
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 查询所有用户信息
     * @return
     */
    public List<Customer> getAllCustomer() throws Exception
    {
        CustomerExample example = new CustomerExample();
        CustomerExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<Customer> customers=customerMapper.selectByExample(example);
        if (customers.size()==0)
        {
            throw new BusinessException("没有任何用户信息...");
        }
        return customers;
    }

    /**
     * 根据id查询用户信息
     * @return
     */
    public Customer getCustomer(Customer customer) throws Exception
    {
        Customer customer1=customerMapper.selectByPrimaryKey(customer.getId());
        if(customer1==null)
        {
            throw new BusinessException("不存在该用户");
        }
        return customer1;
    }

    /**
     * 分页查询所有用户信息
     * @return
     */
    public PageInfo<Customer> getCustomersByPage(Integer pageNum,Integer pageSize,String info) throws Exception
    {
        PageHelper.startPage(pageNum,pageSize);
        CustomerExample customerExample=new CustomerExample();
        CustomerExample.Criteria criteria=customerExample.createCriteria();
        if (info!=null)
          criteria.andAccountLike("%"+info+"%");
        List<Customer> customerList=customerMapper.selectByExample(customerExample);
        PageInfo<Customer> pageInfo=new PageInfo<Customer>(customerList);
        return pageInfo;
    }
}
