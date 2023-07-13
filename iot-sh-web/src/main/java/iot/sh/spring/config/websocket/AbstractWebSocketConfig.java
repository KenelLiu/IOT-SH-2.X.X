package iot.sh.spring.config.websocket;
import iot.sh.constants.IOTConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

//@Configuration
//@EnableWebSocketMessageBroker
public abstract class AbstractWebSocketConfig implements WebSocketMessageBrokerConfigurer{


    @Autowired
    private AuthChannelInterceptor authChannelInterceptor;

    public abstract void registerStompEndpoints(String endPoint);
    /**
     * 开启websocket
     * @EnableWebSocketMessageBroker+WebSocketMessageBrokerConfigurer
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 配置客户端尝试连接地址
        registry
        .addEndpoint(IOTConstants.WebSocketConfig.EndPoint)
        .setAllowedOrigins("*")    // 配置跨域
        .withSockJS();      // 开启sockJS支持，这里可以对不支持stomp的浏览器进行兼容。
    }
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic","/queue","/user");    //user 用于分组 服务端推送，     queue 用户点对点
        registry.setApplicationDestinationPrefixes("/app","/user"); //app 常规数据推送 user 触发订阅  @SubscribeMapping   
        registry.setUserDestinationPrefix("/user");        
    }    
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor);
    }     
    /**
     * 配置发送与接收的消息参数，可以指定消息字节大小，缓存大小，发送超时时间
     * @param registration
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        /*
         * 1. setMessageSizeLimit 设置消息缓存的字节数大小 字节
         * 2. setSendBufferSizeLimit 设置websocket会话时，缓存的大小 字节
         * 3. setSendTimeLimit 设置消息发送会话超时时间，毫秒
         */
        registration.setMessageSizeLimit(10240)
                    .setSendBufferSizeLimit(10240)
                    .setSendTimeLimit(10000);
    }
}