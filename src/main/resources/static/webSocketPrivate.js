
import {addChat,setCurrentSender} from './chatPrivate.js'
import {addFriend} from "./friendPrivate.js";

let sender=null


export const stompClient=new StompJs.Client({
        brokerURL: 'ws://localhost:8080/chat'
    })


export const initializeStompConnection=(currentSender)=>{

    sender=currentSender

    setCurrentSender(sender)
    stompClient.onConnect=(frame)=>{


        stompClient.publish({
            "destination":"/app/message/addPrivateUser",
            "body":sender})

        console.log('Connected: ' + frame);
        stompClient.subscribe(`/queue/reciever/${sender}`,(message)=>{
            const parsedMessage=JSON.parse(message.body)
            // console.log("message was "+parsedMessage+" received by "+sender);

            addChat(parsedMessage.sender, parsedMessage.receiver, parsedMessage.message)

        });

        stompClient.subscribe(`/queue/friends/${sender}`,(message)=>{

            const parsedMessage=message.body
            //we have the name of the friend now we can safely add it

            addFriend(parsedMessage)

        })

    }
    stompClient.activate()
}


