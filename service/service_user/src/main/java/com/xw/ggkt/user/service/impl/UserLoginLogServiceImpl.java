package com.xw.ggkt.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xw.ggkt.model.user.UserLoginLog;
import com.xw.ggkt.user.mapper.UserLoginLogMapper;
import com.xw.ggkt.user.service.UserLoginLogService;
import org.springframework.stereotype.Service;

/**
 * 用户登陆记录表(UserLoginLog)表服务实现类
 *
 * @author xw
 * @since 2023-04-06 15:17:26
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService {

}

