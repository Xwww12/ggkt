package com.xw.ggkt.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.model.user.UserInfo;
import com.xw.ggkt.user.mapper.UserInfoMapper;
import com.xw.ggkt.user.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * 用户表(UserInfo)表服务实现类
 *
 * @author xw
 * @since 2023-04-06 15:17:26
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}

