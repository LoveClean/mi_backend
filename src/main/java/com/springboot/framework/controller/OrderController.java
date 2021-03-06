package com.springboot.framework.controller;

import com.springboot.framework.bo.PageResponseBO;
import com.springboot.framework.bo.ResponseBO;
import com.springboot.framework.constant.Errors;
import com.springboot.framework.controller.request.OrderInsert;
import com.springboot.framework.controller.request.OrderUpdateByPrimaryKey;
import com.springboot.framework.controller.request.UpdateByStatus;
import com.springboot.framework.dao.pojo.Order;
import com.springboot.framework.dto.OrderDTO;
import com.springboot.framework.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author huangpengfei
 * @version 1.0
 * @date 2019/7/9 12:34
 */
@Api(tags = {"订单操作接口"}, produces = "application/json")
@RestController
@RequestMapping("/order/")
public class OrderController extends BaseController {
    @Resource
    private OrderService orderService;

    @ApiOperation(value = "删除", notes = "")
    @DeleteMapping(value = "deleteByPrimaryKey")
    public ResponseBO<Errors> deleteByPrimaryKey(@RequestParam Integer id, HttpServletRequest request) {
        OrderDTO recordDTO = new OrderDTO();
        recordDTO.setOrderId(id);
        recordDTO.setUpdateBy(super.getSessionUser(request).getName());
        return orderService.deleteByPrimaryKey(recordDTO);
    }

    @ApiOperation(value = "新增", notes = "")
    @PostMapping(value = "insertSelective")
    public ResponseBO<Errors> insertSelective(@RequestBody OrderInsert bean, HttpServletRequest request) {
        OrderDTO recordDTO = new OrderDTO(bean.getUserId(), bean.getAddressId(), bean.getOrderTotalPrice(), bean.getOrderDiscountsPrice(), bean.getOrderFreightPrice(), bean.getOrderPayablePrice(), bean.getOrderLogistics(), super.getSessionUser(request).getName());
        return orderService.insertSelective(recordDTO);
    }

    @ApiOperation(value = "查看", notes = "")
    @GetMapping(value = "selectByPrimaryKey")
    public ResponseBO<Order> selectByPrimaryKey(@RequestParam Integer id) {
        return orderService.selectByPrimaryKey(id);
    }

    @ApiOperation(value = "查看列表", notes = "")
    @GetMapping(value = "selectList")
    public PageResponseBO selectList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        return orderService.selectList(pageNum, pageSize);
    }

    @ApiOperation(value = "查看总数", notes = "")
    @GetMapping(value = "selectCount")
    public ResponseBO<Integer> selectCount() {
        return orderService.selectCount();
    }

    @ApiOperation(value = "更新", notes = "")
    @PutMapping(value = "updateByPrimaryKeySelective")
    public ResponseBO<Errors> updateByPrimaryKeySelective(@RequestBody OrderUpdateByPrimaryKey bean, HttpServletRequest request) {
        OrderDTO recordDTO = new OrderDTO(bean.getOrderId(), bean.getUserId(), bean.getAddressId(), bean.getOrderTotalPrice(), bean.getOrderDiscountsPrice(), bean.getOrderFreightPrice(), bean.getOrderPayablePrice(), bean.getOrderLogistics(), super.getSessionUser(request).getName());
        return orderService.updateByPrimaryKeySelective(recordDTO);
    }

    @ApiOperation(value = "更新状态", notes = "")
    @PutMapping(value = "updateByStatus")
    public ResponseBO<Errors> updateByStatus(@RequestBody UpdateByStatus bean, HttpServletRequest request) {
        OrderDTO recordDTO = new OrderDTO();
        recordDTO.setOrderId(bean.getId());
        recordDTO.setStatus(bean.getStatus());
        recordDTO.setUpdateBy(super.getSessionUser(request).getName());
        return orderService.updateByPrimaryKeySelective(recordDTO);
    }
}