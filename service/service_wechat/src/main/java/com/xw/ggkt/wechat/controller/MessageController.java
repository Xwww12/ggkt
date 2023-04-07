package com.xw.ggkt.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.xw.ggkt.wechat.service.MessageService;
import com.xw.result.Result;
import me.chanjar.weixin.common.util.crypto.SHA1;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wechat/message")
public class MessageController {
    private static final String token = "ggkt";

    @Resource
    private MessageService messageService;

    /**
     * 微信服务器发送验证消息，返回echostr内容则接入生效
     * @return
     */
    @GetMapping
    public String verifyToken(HttpServletRequest request) {
        String signature = request.getParameter("signature");   // 加密签名
        String timestamp = request.getParameter("timestamp");   // 时间戳
        String nonce = request.getParameter("nonce");   // 随机值
        String echostr = request.getParameter("echostr");   // 随机字符串
        if (checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return null;
    }

    /**
     * 校验加密签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    private boolean checkSignature(String signature, String timestamp, String nonce) {
        return true;
    }

    /**
     * 接收微信服务器发送来的消息
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping
    public String receiveMessage(HttpServletRequest request) throws Exception {
        Map<String, String> param = parseXml(request);
        return messageService.receiveMessage(param);
    }

    @GetMapping("/pushPayMessage")
    public Result pushPayMessage() {
        messageService.pushPayMessage(1L);
        return Result.ok();
    }


    private Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        ServletInputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        inputStream.close();
        inputStream = null;
        return map;
    }
}
