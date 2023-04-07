package com.xw.ggkt.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.model.order.OrderDetail;
import com.xw.ggkt.order.mapper.OrderDetailMapper;
import com.xw.ggkt.order.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * 订单明细 订单明细(OrderDetail)表服务实现类
 *
 * @author xw
 * @since 2023-04-05 21:35:33
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}

