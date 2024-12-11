package com.example.demochatroom;

import com.example.demochatroom.messages.Message;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@AllArgsConstructor
public class WebSocketEventListener {

    @Autowired
    SimpMessageSendingOperations messageSendingOperations;
//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectEvent sessionConnectEvent){
//
//        StompHeaderAccessor stompHeaderAccessor=StompHeaderAccessor.wrap(sessionConnectEvent.getMessage());
//        String username=(String) stompHeaderAccessor.getSessionAttributes().get("user");
//        System.out.println("welp"+username);
//        if(username!=null){
//            var obj=Message.builder()
//                    .sender(username)
//                    .message("joins the fray")
//                            .build();
//            messageSendingOperations.convertAndSend("/topics/hello",obj);
//
//        }
//    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent){
        StompHeaderAccessor stompHeaderAccessor=StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        String username=(String) stompHeaderAccessor.getSessionAttributes().get("user");
        String color=(String) stompHeaderAccessor.getSessionAttributes().get("color");
        if(username!=null){
            var obj= Message.builder()
                    .sender(username)
                    .message("leaves the fray")
                    .color(color)

                            .build();

            messageSendingOperations.convertAndSend("/topics/hello",obj);

        }
    }
}
