package com.xw.ggkt.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.model.order.PaymentInfo;
import com.xw.ggkt.order.mapper.PaymentInfoMapper;
import com.xw.ggkt.order.service.PaymentInfoService;
import org.springframework.stereotype.Service;

/**
 * 支付信息表(PaymentInfo)表服务实现类
 *
 * @author xw
 * @since 2023-04-05 21:35:33
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

}

