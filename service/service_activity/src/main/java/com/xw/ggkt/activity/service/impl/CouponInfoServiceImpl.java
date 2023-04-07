package com.xw.ggkt.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.activity.mapper.CouponInfoMapper;
import com.xw.ggkt.activity.service.CouponInfoService;
import com.xw.ggkt.activity.service.CouponUseService;
import com.xw.ggkt.client.UserInfoFeignClient;
import com.xw.ggkt.model.activity.CouponInfo;
import com.xw.ggkt.model.activity.CouponUse;
import com.xw.ggkt.model.order.OrderInfo;
import com.xw.ggkt.model.user.UserInfo;
import com.xw.ggkt.vo.activity.CouponUseQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 优惠券信息(CouponInfo)表服务实现类
 *
 * @author xw
 * @since 2023-04-05 22:13:29
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Resource
    private UserInfoFeignClient userInfoFeignClient;

    @Resource
    private CouponUseService couponUseService;

    @Override
    public IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo) {
        Long couponId = couponUseQueryVo.getCouponId();
        String couponStatus = couponUseQueryVo.getCouponStatus();
        String getTimeBegin = couponUseQueryVo.getGetTimeBegin();
        String getTimeEnd = couponUseQueryVo.getGetTimeEnd();

        // 判断参数是否合法
        LambdaQueryWrapper<CouponUse> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(couponId)) {
            wrapper.eq(CouponUse::getCouponId,couponId);
        }
        if(!StringUtils.isEmpty(couponStatus)) {
            wrapper.eq(CouponUse::getCouponStatus,couponStatus);
        }
        if(!StringUtils.isEmpty(getTimeBegin)) {
            wrapper.ge(CouponUse::getCreateTime,getTimeBegin);
        }
        if(!StringUtils.isEmpty(getTimeEnd)) {
            wrapper.le(CouponUse::getCreateTime,getTimeEnd);
        }

        IPage<CouponUse> page = couponUseService.page(pageParam, wrapper);
        List<CouponUse> couponUseList = page.getRecords();
        couponUseList.stream()
                .forEach(item -> {
                    this.getUserInfoById(item); // 封装用户昵称和手机号
                });
        return page;
    }

    private CouponUse getUserInfoById(CouponUse couponUse) {
        Long userId = couponUse.getUserId();
        if (!StringUtils.isEmpty(userId)) {
            UserInfo userInfo = userInfoFeignClient.getById(userId);
            if (userInfo != null) {
                couponUse.getParam().put("nickName", userInfo.getNickName());
                couponUse.getParam().put("phone", userInfo.getPhone());
            }
        }
        return couponUse;
    }
}

