package com.xw.ggkt.wechat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xw.ggkt.model.wechat.Menu;
import com.xw.ggkt.vo.wechat.MenuVo;

import java.util.List;

/**
 * 订单明细 订单明细(Menu)表服务接口
 *
 * @author xw
 * @since 2023-04-06 16:50:05
 */
public interface MenuService extends IService<Menu> {

    List<MenuVo> findMenuInfo();

    List<Menu> findMenuOneInfo();

    void syncMenu();

    void removeMenu();
}

