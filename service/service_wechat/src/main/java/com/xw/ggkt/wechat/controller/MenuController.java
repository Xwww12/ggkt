package com.xw.ggkt.wechat.controller;


import com.alibaba.fastjson.JSONObject;
import com.xw.ggkt.model.wechat.Menu;
import com.xw.ggkt.vo.wechat.MenuVo;
import com.xw.ggkt.wechat.service.MenuService;
import com.xw.ggkt.wechat.utils.ConstantPropertiesUtil;
import com.xw.ggkt.wechat.utils.HttpClientUtils;
import com.xw.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/wechat/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    //获取所有菜单，按照一级和二级菜单封装
    @GetMapping("findMenuInfo")
    public Result findMenuInfo() {
        List<MenuVo> list = menuService.findMenuInfo();
        return Result.ok(list);
    }

    //获取所有一级菜单
    @GetMapping("findOneMenuInfo")
    public Result findOneMenuInfo() {
        List<Menu> list = menuService.findMenuOneInfo();
        return Result.ok(list);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Menu menu = menuService.getById(id);
        return Result.ok(menu);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody Menu menu) {
        menuService.save(menu);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        menuService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        menuService.removeByIds(idList);
        return Result.ok(null);
    }

    /**
     * 获取access_token
     * access_token是公众号的全局唯一接口调用凭据
     * https请求方式: GET https://api.weixin.qq.com/cgi-bin/token?
     * grant_type=client_credential&appid=APPID&secret=APPSECRET
     * @return
     */
    @GetMapping("getAccessToken")
    public Result getAccessToken() {
        try {
            // 拼接请求地址
            StringBuffer buffer = new StringBuffer();
            buffer.append("https://api.weixin.qq.com/cgi-bin/token");
            buffer.append("?grant_type=client_credential");
            buffer.append("&appid=%s");
            buffer.append("&secret=%s");
            String url = String.format(buffer.toString(),
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            // 发送请求
            String tokenString = HttpClientUtils.get(url);
            // 获取access_token
            JSONObject jsonObject = JSONObject.parseObject(tokenString);
            String access_token = jsonObject.getString("access_token");
            return Result.ok(access_token);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }

    /**
     * 更新菜单接口
     * @return
     */
    @ApiOperation(value = "同步菜单")
    @GetMapping("syncMenu")
    public Result createMenu() {
        menuService.syncMenu();
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("removeMenu")
    public Result removeMenu() {
        menuService.removeMenu();
        return Result.ok();

    }

}
