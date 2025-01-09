package com.example.demochatroom.messages;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

    private String sender;
//    private String recipient;
    private String message;
    private String color;


//    public Message(){
//
//    }
//
//
//    public Message(String sender,String message){
//
//        this.sender=sender;
//        this.message=message;
////        this.recipient=recipient;
////        this.content=content;
//    }


//    public String getRecipient() {
//        return recipient;
//    }
//
//    public void setRecipient(String recipient) {
//        this.recipient = recipient;
//    }
//



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
