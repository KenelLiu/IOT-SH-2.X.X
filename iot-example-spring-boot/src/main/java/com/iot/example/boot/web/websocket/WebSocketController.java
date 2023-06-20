package com.iot.example.boot.web.websocket;

import java.util.List;

import com.iot.sh.web.controller.websocket.BaseWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
@Slf4j
public class WebSocketController extends BaseWebSocket {

    @SubscribeMapping("/{tenantId}/alarm")
    public String subscribe(@DestinationVariable String tenantId,StompHeaderAccessor accessor){
    	List<String> nativeHeader = accessor.getNativeHeader("Authorization");
    	log.info("body="+nativeHeader);
        return  "subscribe 分组 /"+tenantId+"/alarm success";
    }
   
    
}
