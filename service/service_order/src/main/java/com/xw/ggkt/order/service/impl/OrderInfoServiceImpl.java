package com.xw.ggkt.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.model.order.OrderDetail;
import com.xw.ggkt.model.order.OrderInfo;
import com.xw.ggkt.order.mapper.OrderInfoMapper;
import com.xw.ggkt.order.service.OrderDetailService;
import com.xw.ggkt.order.service.OrderInfoService;
import com.xw.ggkt.vo.order.OrderInfoQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单表 订单表(OrderInfo)表服务实现类
 *
 * @author xw
 * @since 2023-04-05 21:35:33
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Resource
    private OrderDetailService orderDetailService;

    @Override
    public Map<String, Object> findPageOrderInfo(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo) {
        // orderInfoQueryVo获取查询条件
        Long userId = orderInfoQueryVo.getUserId();
        String outTradeNo = orderInfoQueryVo.getOutTradeNo();
        String phone = orderInfoQueryVo.getPhone();
        String createTimeEnd = orderInfoQueryVo.getCreateTimeEnd();
        String createTimeBegin = orderInfoQueryVo.getCreateTimeBegin();
        Integer orderStatus = orderInfoQueryVo.getOrderStatus();

        // 判断条件值是否为空，不为空，进行条件封装
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(orderStatus)) {
            wrapper.eq(OrderInfo::getOrderStatus,orderStatus);
        }
        if(!StringUtils.isEmpty(userId)) {
            wrapper.eq(OrderInfo::getUserId,userId);
        }
        if(!StringUtils.isEmpty(outTradeNo)) {
            wrapper.eq(OrderInfo::getOutTradeNo,outTradeNo);
        }
        if(!StringUtils.isEmpty(phone)) {
            wrapper.eq(OrderInfo::getPhone,phone);
        }
        if(!StringUtils.isEmpty(createTimeBegin)) {
            wrapper.ge(OrderInfo::getCreateTime,createTimeBegin);
        }
        if(!StringUtils.isEmpty(createTimeEnd)) {
            wrapper.le(OrderInfo::getCreateTime,createTimeEnd);
        }
        // 调用实现条件分页查询
        Page<OrderInfo> pages = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pages.getTotal();
        long pageCount = pages.getPages();
        List<OrderInfo> records = pages.getRecords();
        //订单里面包含详情内容，封装详情数据，根据订单id查询详情
        records.stream().forEach(item -> {
            this.getOrderDetail(item);
        });

        //所有需要数据封装map集合，最终返回
        Map<String,Object> map = new HashMap<>();
        map.put("total",totalCount);
        map.put("pageCount",pageCount);
        map.put("records",records);
        return map;
    }

    // 查询订单详情数据
    private OrderInfo getOrderDetail(OrderInfo orderInfo) {
        //订单id
        Long id = orderInfo.getId();
        //查询订单详情
        OrderDetail orderDetail = orderDetailService.getById(id);
        if(orderDetail != null) {
            String courseName = orderDetail.getCourseName();
            orderInfo.getParam().put("courseName",courseName);
        }
        return orderInfo;
    }
}

