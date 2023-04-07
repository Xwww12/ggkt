package com.xw.ggkt.wechat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.wechat.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单明细 订单明细(Menu)表数据库访问层
 *
 * @author xw
 * @since 2023-04-06 16:50:05
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

}

