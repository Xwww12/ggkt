package com.xw.ggkt.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xw.ggkt.model.order.OrderInfo;
import com.xw.ggkt.vo.order.OrderInfoQueryVo;

import java.util.Map;

/**
 * 订单表 订单表(OrderInfo)表服务接口
 *
 * @author xw
 * @since 2023-04-05 21:35:33
 */
public interface OrderInfoService extends IService<OrderInfo> {

    Map<String, Object> findPageOrderInfo(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);
}

