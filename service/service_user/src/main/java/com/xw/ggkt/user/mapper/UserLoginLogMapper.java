package com.xw.ggkt.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.user.UserLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户登陆记录表(UserLoginLog)表数据库访问层
 *
 * @author xw
 * @since 2023-04-06 15:17:26
 */
@Mapper
public interface UserLoginLogMapper extends BaseMapper<UserLoginLog> {

}

