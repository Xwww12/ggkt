package com.xw.ggkt.wechat.service.impl;

import com.xw.ggkt.client.CourseFeignClient;
import com.xw.ggkt.model.vod.Course;
import com.xw.ggkt.wechat.service.MessageService;
import lombok.SneakyThrows;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private CourseFeignClient courseFeignClient;

    @Autowired
    private WxMpService wxMpService;

    /**
     * 接收消息
     * @param param
     * @return
     */
    @Override
    public String receiveMessage(Map<String, String> param) {
        String content = "";
        // 根据消息类型回复消息
        try {
            String msgType = param.get("MsgType");
            switch (msgType) {
                case "text":    // 文本消息
                    content = search(param);
                    break;
                case "event":
                    String event = param.get("Event");
                    String eventKey = param.get("EventKey");
                    if ("subscribe".equals(event)) {    // 关注
                        content = this.subscribe(param);
                    } else if ("unsubscribe".equals(event)) {   // 取关
                        content = this.unsubscribe(param);
                    } else if ("CLICK".equals(event) && "aboutUs".equals(eventKey)) {   // 关于
                        content = this.aboutUs(param);
                    } else {
                        content = "success";
                    }
                    break;
                default:
                    content = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    @SneakyThrows
    @Override
    public void pushPayMessage(long id) {
        String openid = "o-f-55v-C2rw0k3e7i6iscZIRElM";
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openid)//要推送的用户openid
                .templateId("37jusDoqXS03ZBO4q5_LruEu5MyOt6iImJ_dZYbS5Y0")//模板id
                .url("http://ggkt2.vipgz1.91tunnel.com/#/pay/"+openid)//点击模板消息要访问的网址
                .build();
        //3,如果是正式版发送消息，，这里需要配置你的信息
        templateMessage.addData(new WxMpTemplateData("first", "亲爱的用户：您有一笔订单支付成功。", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword1", "1314520", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword2", "java基础课程", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword3", "2022-01-11", "#272727"));
        templateMessage.addData(new WxMpTemplateData("keyword4", "100", "#272727"));
        templateMessage.addData(new WxMpTemplateData("remark", "感谢你购买课程，如有疑问，随时咨询！", "#272727"));
        String msg = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        System.out.println(msg);
    }


    /**
     * 处理关键字搜索事件
     * 消息格式
     * <xml>
     *   <ToUserName><![CDATA[toUser]]></ToUserName>
     *   <FromUserName><![CDATA[fromUser]]></FromUserName>
     *   <CreateTime>12345678</CreateTime>
     *   <MsgType><![CDATA[news]]></MsgType>
     *   <ArticleCount>1</ArticleCount>
     *   <Articles>
     *     <item>
     *       <Title><![CDATA[title1]]></Title>
     *       <Description><![CDATA[description1]]></Description>
     *       <PicUrl><![CDATA[picurl]]></PicUrl>
     *       <Url><![CDATA[url]]></Url>
     *     </item>
     *   </Articles>
     * </xml>
     */
    private String search(Map<String, String> param) {
        String fromUserName = param.get("FromUserName");    // 发送人
        String toUserName = param.get("ToUserName");    // 接收人
        String content = param.get("Content");  // 发送的内容

        long createTime = new Date().getTime() / 1000;
        StringBuffer text = new StringBuffer();     // 返回的内容
        List<Course> courseList = courseFeignClient.findByKeyword(content);
        if (CollectionUtils.isEmpty(courseList)) {
            text = text(param, "请重新输入关键字，没有匹配到相关课程");
        } else {
            // 随机返回一个
            Random random = new Random();
            int num = random.nextInt(courseList.size());
            Course course = courseList.get(num);

            StringBuffer articles = new StringBuffer();
            articles.append("<item>");
            articles.append("<Title><![CDATA["+course.getTitle()+"]]></Title>");
            articles.append("<Description><![CDATA["+course.getTitle()+"]]></Description>");
            articles.append("<PicUrl><![CDATA["+course.getCover()+"]]></PicUrl>");
            articles.append("<Url><![CDATA[http://glkt.atguigu.cn/#/liveInfo/"+course.getId()+"]]></Url>");
            articles.append("</item>");

            text.append("<xml>");
            text.append("<ToUserName><![CDATA["+fromUserName+"]]></ToUserName>");
            text.append("<FromUserName><![CDATA["+toUserName+"]]></FromUserName>");
            text.append("<CreateTime><![CDATA["+createTime+"]]></CreateTime>");
            text.append("<MsgType><![CDATA[news]]></MsgType>");
            text.append("<ArticleCount><![CDATA[1]]></ArticleCount>");
            text.append("<Articles>");
            text.append(articles);
            text.append("</Articles>");
            text.append("</xml>");
        }
        return text.toString();
    }

    /**
     * 处理关注事件
     * @param param
     * @return
     */
    private String subscribe(Map<String, String> param) {
        //处理业务
        return this.text(param, "感谢你的关注").toString();
    }

    /**
     * 处理取消关注事件
     * @param param
     * @return
     */
    private String unsubscribe(Map<String, String> param) {
        //处理业务
        return "success";
    }

    /**
     * 关于
     * @param param
     * @return
     */
    private String aboutUs(Map<String, String> param) {
        return text(param, "about about about").toString();
    }


    /**
     * 回复文本
     * @param param
     * @param content
     * @return
     */
    private StringBuffer text(Map<String, String> param, String content) {
        String fromusername = param.get("FromUserName");
        String tousername = param.get("ToUserName");
        //单位为秒，不是毫秒
        Long createTime = new Date().getTime() / 1000;
        StringBuffer text = new StringBuffer();
        text.append("<xml>");
        text.append("<ToUserName><![CDATA["+fromusername+"]]></ToUserName>");
        text.append("<FromUserName><![CDATA["+tousername+"]]></FromUserName>");
        text.append("<CreateTime><![CDATA["+createTime+"]]></CreateTime>");
        text.append("<MsgType><![CDATA[text]]></MsgType>");
        text.append("<Content><![CDATA["+content+"]]></Content>");
        text.append("</xml>");
        return text;
    }
}
