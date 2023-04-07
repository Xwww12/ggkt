package com.xw.ggkt.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.order.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息表(PaymentInfo)表数据库访问层
 *
 * @author xw
 * @since 2023-04-05 21:35:33
 */
@Mapper
public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {

}

