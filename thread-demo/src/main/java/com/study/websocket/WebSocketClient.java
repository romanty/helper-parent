package com.study.websocket;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;

/**
 * @author yutong on 2016/12/22.
 */
@ClientEndpoint
public class WebSocketClient {
    private String deviceId;

    private Session session;

    public WebSocketClient() {
    }

    public WebSocketClient(String deviceId) {
        this.deviceId = deviceId;
    }

    protected boolean start() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        String uri = "ws://localhost:8080/webSocket?role=1&group=recommend&dldm="+ deviceId;
        System.out.println("Connecting to " + uri);
        try {
            session = container.connectToServer(WebSocketClient.class, URI.create(uri));
            System.out.println("count: " + deviceId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        for (int i = 1; i< 50000; i++) {
            WebSocketClient wSocketTest = new WebSocketClient(String.valueOf(i));
            if (!wSocketTest.start()) {
                System.out.println("测试结束！");
                break;
            }
        }
    }
}
