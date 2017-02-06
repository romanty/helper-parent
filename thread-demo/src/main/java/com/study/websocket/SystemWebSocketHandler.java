package com.study.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author yutong on 2017/1/6.
 */
@Service
public class SystemWebSocketHandler implements WebSocketHandler {
    private static final Logger LOGGER;

    private static final ArrayList<WebSocketSession> users;

    static {
        users = new ArrayList<>();
        LOGGER = LoggerFactory.getLogger(SystemWebSocketHandler.class);
    }

//    @Autowired
//    private WebSocketService webSocketService;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        LOGGER.debug("connection to the websocket success.......");
        users.add(webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        String userName = (String) webSocketSession.getAttributes().get("userName");
        sendMessageToUser(userName, (TextMessage) webSocketMessage);

        sendMessageToOthers(userName, new TextMessage(""));
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }
        users.remove(webSocketSession);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        LOGGER.debug("websocket connection is closed......");
        users.remove(webSocketSession);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageToOthers(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("userName").equals(userName)) {
                continue;
            }
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("userName").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
