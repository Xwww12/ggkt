package com.xw.ggkt.wechat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.JsonArray;
import com.xw.ggkt.model.wechat.Menu;
import com.xw.ggkt.vo.wechat.MenuVo;
import com.xw.ggkt.wechat.mapper.MenuMapper;
import com.xw.ggkt.wechat.service.MenuService;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单明细 订单明细(Menu)表服务实现类
 *
 * @author xw
 * @since 2023-04-06 16:50:05
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private WxMpService wxMpService;

    @Override
    public List<MenuVo> findMenuInfo() {
        // 获取所有菜单
        List<Menu> menuList = baseMapper.selectList(null);

        // 根据层级封装
        List<Menu> oneMenuList = menuList.stream()
                .filter(menu -> menu.getParentId().longValue() == 0)
                .collect(Collectors.toList());

        List<MenuVo> menuVoList = new ArrayList<>();
        for (Menu oneMenu : oneMenuList) {
            MenuVo oneMenuVo = new MenuVo();
            BeanUtils.copyProperties(oneMenu, oneMenuVo);

            List<Menu> twoMenuList = menuList.stream()
                    .filter(menu -> menu.getParentId().longValue() == oneMenuVo.getId())
                    .sorted(Comparator.comparing(Menu::getSort))
                    .collect(Collectors.toList());
            // 将二级菜单封装到一级菜单里
            List<MenuVo> children = new ArrayList<>();
            for (Menu twoMenu : twoMenuList) {
                MenuVo twoMenuVo = new MenuVo();
                BeanUtils.copyProperties(twoMenu, twoMenuVo);
                children.add(twoMenuVo);
            }
            oneMenuVo.setChildren(children);
            menuVoList.add(oneMenuVo);
        }
        return menuVoList;
    }

    @Override
    public List<Menu> findMenuOneInfo() {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, 0);
        List<Menu> menuList = baseMapper.selectList(wrapper);

        return menuList;
    }

    @SneakyThrows
    @Override
    public void syncMenu() {
        List<MenuVo> menuVoList = findMenuInfo();

        // 菜单
        JSONArray buttonList = new JSONArray();
        for (MenuVo oneMenuVo : menuVoList) {
            JSONObject one = new JSONObject();  // 一级菜单
            one.put("name", oneMenuVo.getName());

            JSONArray subButton = new JSONArray();
            for (MenuVo twoMenuVo : oneMenuVo.getChildren()) {
                JSONObject view = new JSONObject(); // 二级菜单
                view.put("type", twoMenuVo.getType());
                if (twoMenuVo.getType().equals("view")) {
                    view.put("name", twoMenuVo.getName());
                    view.put("url", "http://ggkt2.vipgz1.91tunnel.com/#" + twoMenuVo.getUrl());
                } else {
                    view.put("name", twoMenuVo.getName());
                    view.put("key", twoMenuVo.getMeunKey());
                }
                subButton.add(view);
            }
            one.put("sub_button", subButton);
            buttonList.add(one);
        }
        JSONObject button = new JSONObject();
        button.put("button", buttonList);
        wxMpService.getMenuService().menuCreate(button.toJSONString());
    }

    @SneakyThrows
    @Override
    public void removeMenu() {
        wxMpService.getMenuService().menuDelete();
    }
}

