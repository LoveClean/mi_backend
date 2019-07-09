package com.springboot.framework.service.impl;

import com.github.pagehelper.PageHelper;
import com.springboot.framework.bo.ResponseBO;
import com.springboot.framework.constant.Errors;
import com.springboot.framework.bo.PageResponseBO;
import com.springboot.framework.dao.pojo.Admin;
import com.springboot.framework.dao.mapper.AdminMapper;
import com.springboot.framework.dto.AdminDTO;
import com.springboot.framework.service.AdminService;
import com.springboot.framework.util.*;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Override
    public ResponseBO<Errors> deleteByPrimaryKey(AdminDTO recordDTO) {
        //2.创建entity
        Admin record = new Admin(recordDTO);
        record.setStatus((byte) -1);
        //3.响应校验
        if (adminMapper.updateByPrimaryKeySelective(record) == 0) {
            return ResponseBOUtil.fail("删除失败");
        }
        return ResponseBOUtil.success(Errors.SUCCESS);
    }

    @Override
    public ResponseBO<Errors> insertSelective(AdminDTO recordDTO) {
        //1.请求校验
        Errors errors = validRequest(recordDTO, "insertSelective");
        if (errors.code != 0) {
            return ResponseBOUtil.fail(errors);
        }
        //2.创建entity
        Admin record = new Admin(recordDTO);
        record.setPassword(MD5Util.MD5(record.getPassword()));
        //3.响应校验
        if (adminMapper.insertSelective(record) == 0) {
            return ResponseBOUtil.fail("添加失败");
        }
        return ResponseBOUtil.success(Errors.SUCCESS);
    }

    @Override
    public ResponseBO<Admin> login(AdminDTO recordDTO) {
        //1.请求校验
        Errors errors = validRequest(recordDTO, "login");
        if (errors.code != 0) {
            return ResponseBOUtil.fail(errors);
        }
        //2.创建entity
        Admin admin = adminMapper.login(recordDTO.getLoginKey(), MD5Util.MD5(recordDTO.getPassword()));
        //3.响应校验
        if (admin == null) {
            return ResponseBOUtil.fail(Errors.USER_LOGIN_ERROR);
        }
        if (admin.getStatus() == 0) {
            return ResponseBOUtil.fail(Errors.SYSTEM_NO_ACCESS);
        }
        return ResponseBOUtil.success(admin);
    }

    @Override
    public ResponseBO<Admin> selectByPrimaryKey(Integer id) {
        return ResponseBOUtil.success(adminMapper.selectByPrimaryKey(id));
    }

    @Override
    public PageResponseBO selectList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Example example = new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("status", -1);
        example.orderBy("createDate").desc();

        List<Admin> adminList = adminMapper.selectByExample(example);
        return PageUtil.page(adminList);
    }

    @Override
    public PageResponseBO selectListByPhone(String phone, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Example example = new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 1);
        criteria.andLike("createBy", "%" + phone + "%");
        example.orderBy("createDate").desc();

        List<Admin> adminList = adminMapper.selectByExample(example);
        return PageUtil.page(adminList);
    }

    @Override
    public ResponseBO<Integer> selectCount() {
        Admin record = new Admin();
        record.setStatus((byte) 1);
        return ResponseBOUtil.success(adminMapper.selectCount(record));
    }

    @Override
    public ResponseBO<Errors> updateByPrimaryKeySelective(AdminDTO recordDTO) {
        //1.请求校验
        Errors errors = validRequest(recordDTO, "updateByPrimaryKeySelective");
        if (errors.code != 0) {
            return ResponseBOUtil.fail(errors);
        }
        //2.创建entity
        Admin admin = new Admin(recordDTO);
        if (!StringUtil.isEmpty(admin.getPassword())) {
            admin.setPassword(MD5Util.MD5(admin.getPassword()));
        }
        //3.响应校验
        if (adminMapper.updateByPrimaryKeySelective(admin) == 0) {
            return ResponseBOUtil.fail("更新失败");
        }
        return ResponseBOUtil.success(Errors.SUCCESS);
    }

    @Override
    public ResponseBO<Errors> updateByPassword(Integer id, String oldPassword, String newPassword, String updateBy) {
        int updateCount = adminMapper.updateByPassword(id, MD5Util.MD5(oldPassword), MD5Util.MD5(newPassword), updateBy);
        if (updateCount == 0) {
            return ResponseBOUtil.fail(Errors.USER_OLD_PASSWORD_ERROR);
        }
        return ResponseBOUtil.success(Errors.SUCCESS);
    }

    private Errors validRequest(AdminDTO recordDTO, String type) {
        Admin validRequest;
        Example example = new Example(Admin.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("status", -1);
        switch (type) {
            case "insertSelective":
                criteria.andEqualTo("phone", recordDTO.getPhone());
                validRequest = adminMapper.selectOneByExample(example);
                if (validRequest != null) {
                    return Errors.USER_MOBILE_EXISTS;
                }
                criteria.orEqualTo("account", recordDTO.getAccount());
                validRequest = adminMapper.selectOneByExample(example);
                if (validRequest != null) {
                    return Errors.USER_USERNAME_SAME;
                }
                break;
            case "login":
                if (StringUtil.isEmpty(recordDTO.getLoginKey()) || StringUtil.isEmpty(recordDTO.getPassword())) {
                    return Errors.SYSTEM_REQUEST_PARAM_ERROR;
                }
                break;
            case "updateByPrimaryKeySelective":
                criteria.andEqualTo("phone", recordDTO.getPhone());
                validRequest = adminMapper.selectOneByExample(example);
                if (validRequest != null && !validRequest.getId().equals(recordDTO.getId())) {
                    return Errors.USER_MOBILE_EXISTS;
                }
                break;
            default:
                return Errors.SUCCESS;
        }
        return Errors.SUCCESS;
    }
}
