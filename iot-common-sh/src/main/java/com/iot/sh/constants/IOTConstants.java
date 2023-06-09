package com.iot.sh.constants;

/**
 * @author Kenel Liu
 */
public interface IOTConstants {
    public static class Session{
        public final static String Session_User="session_user";
    }

    public static class WebSocketConfig{
        public final static String EndPoint="/websocket/bodyiot";
        public final static String Topic_Alarm="/topic/alarm";
    }
}
