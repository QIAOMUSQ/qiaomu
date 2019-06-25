package com.qiaomu.websocket.constants;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author laochunyu
 */
public class Constants {


    public static String DEFAULT_HOST = "localhost";
    @Value("${server.port}")
    public static int DEFAULT_PORT;
    @Value("${websocketUrl}")
    public static String WEBSOCKET_URL;
}
