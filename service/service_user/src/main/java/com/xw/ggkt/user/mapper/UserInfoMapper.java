package com.xw.ggkt.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xw.ggkt.model.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(UserInfo)表数据库访问层
 *
 * @author xw
 * @since 2023-04-06 15:17:25
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

}

