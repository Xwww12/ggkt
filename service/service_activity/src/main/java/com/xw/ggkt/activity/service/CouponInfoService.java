package com.xw.ggkt.activity.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xw.ggkt.model.activity.CouponInfo;
import com.xw.ggkt.model.activity.CouponUse;
import com.xw.ggkt.vo.activity.CouponUseQueryVo;

/**
 * 优惠券信息(CouponInfo)表服务接口
 *
 * @author xw
 * @since 2023-04-05 22:13:29
 */
public interface CouponInfoService extends IService<CouponInfo> {

    IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo);
}

