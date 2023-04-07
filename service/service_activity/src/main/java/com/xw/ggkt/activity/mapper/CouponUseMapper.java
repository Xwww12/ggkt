package com.xw.ggkt.activity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.activity.CouponUse;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领用表(CouponUse)表数据库访问层
 *
 * @author xw
 * @since 2023-04-05 22:13:29
 */
@Mapper
public interface CouponUseMapper extends BaseMapper<CouponUse> {

}

