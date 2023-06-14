package com.iot.sh.web.controller.websocket;

import com.iot.sh.constants.IOTConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

/**
 * @author Kenel Liu
 */
@Slf4j
public class BaseWebSocket {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 发送消息给所有订阅者,广播
     * stompClient.subscribe('/topic/alarm', function (data) {*
     *         	showGreeting(data.body);//解释数据
     *  });
     *  stompClient.send("/app/device/pushTopic", {}, JSON.stringify(dataJson));
     * @param accessor
     * @param body
     */
    @MessageMapping("/device/pushTopic")
    public void pushTopic(StompHeaderAccessor accessor, String body){
        String strJson="{}";
        Long tenantId=1L;
        //========广播形式=======//
        messagingTemplate.convertAndSend(IOTConstants.WebSocketConfig.Topic_Alarm, strJson);

    }

    /**
     * stompClient.subscribe('/user/'+tenantId+'/alarm', function (data) {
     *             showGreeting(data.body);
     *         });
     *
     * stompClient.send("/app/device/pushTopicToGroup", {}, JSON.stringify(dataJson));
     * 发送消息给分组
     * @param accessor
     * @param body
     */
    @MessageMapping("/device/pushTopicToGroup")
    public void pushTopicToGroup(StompHeaderAccessor accessor, String body){
        String strJson="{}";
        Long tenantId=1L;
        //========分组形式=======//
        messagingTemplate.convertAndSendToUser(""+tenantId, "/alarm",strJson);
    }

    /**
     * 此项功仅用于点对点测试
     * 	    stompClient.subscribe('/user/queue/chat', function (data) {
     *             showGreeting(data.body);
     *         });
     *  stompClient.send("/app/person/chat", {}, JSON.stringify({'mac': $("#mac").val(),'receiver':chatUser}));
     * @param accessor
     * @param body
     */
    @MessageMapping("/person/chat")
    public void sendChatMessage(StompHeaderAccessor accessor,String body) {
        try{
            String receiver="user1";//对应前端user1/user2真实用户
            String strJson="{}";
            messagingTemplate.convertAndSendToUser(receiver, "/queue/chat",strJson);
        }catch(Throwable ex){
            log.error("person chat error:"+ex.getMessage(),ex);
        }
    }
}
