package com.xw.ggkt.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单表 订单表(OrderInfo)表数据库访问层
 *
 * @author xw
 * @since 2023-04-05 21:35:33
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

}

