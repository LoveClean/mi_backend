package com.springboot.framework.service;

import com.springboot.framework.constant.Errors;
import com.springboot.framework.bo.PageResponseBO;
import com.springboot.framework.dao.pojo.Admin;
import com.springboot.framework.dto.AdminDTO;
import com.springboot.framework.bo.ResponseBO;

/**
 * @author huangpengfei
 * @version 1.0
 * @date 2019/7/6
 */
public interface AdminService {
    /**
     * 删除
     *
     * @param adminDTO 管理员传输对象
     * @return ResponseBO<Errors>
     */
    ResponseBO<Errors> deleteByPrimaryKey(AdminDTO adminDTO);

    /**
     * 新增
     *
     * @param adminDTO 管理员传输对象
     * @return ResponseBO<Errors>
     */
    ResponseBO<Errors> insertSelective(AdminDTO adminDTO);

    /**
     * 登陆
     *
     * @param adminDTO 管理员传输对象
     * @return ResponseBO<Admin>
     */
    ResponseBO<Admin> login(AdminDTO adminDTO);

    /**
     * 查询
     *
     * @param id 商品主键
     * @return ResponseBO<Admin>
     */
    ResponseBO<Admin> selectByPrimaryKey(Integer id);

    /**
     * 列表查询
     *
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return PageResponseBO
     */
    PageResponseBO selectList(Integer pageNum, Integer pageSize);

    /**
     * 列表查询（根据手机号）
     *
     * @param phone    手机号
     * @param pageNum  页码
     * @param pageSize 页面大小
     * @return PageResponseBO
     */
    PageResponseBO selectListByPhone(String phone, Integer pageNum, Integer pageSize);

    /**
     * 总数查询
     *
     * @return ResponseBO<Integer>
     */
    ResponseBO<Integer> selectCount();

    /**
     * 更新
     *
     * @param adminDTO 管理员传输对象
     * @return ResponseBO<Errors>
     */
    ResponseBO<Errors> updateByPrimaryKeySelective(AdminDTO adminDTO);

    /**
     * 更新（密码）
     *
     * @param id          管理员主键
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param updateBy    更新人
     * @return ResponseBO<Errors>
     */
    ResponseBO<Errors> updateByPassword(Integer id, String oldPassword, String newPassword, String updateBy);
}
