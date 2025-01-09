import {stompClient} from "./webSocketPrivate.js"

import {addFriend} from "./friendPrivate.js";

import {addProfileHeader} from "./ui.js";


let sender=null
let receiver=null

let friends=[]

export const setCurrentSender=(sender1)=>{

    if(sender===null){
        sender=sender1
    }
}
export const setCurrentReceiver=(receiver1)=>{

    if(receiver1!=null) {
        receiver = receiver1
    }
}

export const sendMessagePrivate=(message)=>{
    const newPrivateMessage={
        "sender":sender,
        "message":message.content,
        "receiver":receiver
    }
    if(!receiver||!message){return}

    stompClient.publish({

        destination:"/app/message/sendPrivate",
        body:JSON.stringify(newPrivateMessage)
    })
}
export const addChat=(sender1,receiver1,message)=>{

    if(message==null||message===''||receiver===null||(sender1!==sender&&receiver!==sender1)){
        return;
    }

    //assume three details for now , sender ,receiver and message
    const chatBox=document.querySelector(".chatBox");
    const chat=document.createElement('div')
    chat.style.padding="10px"
    chat.className="chat"

    const splitMessage=message.split('|')

    let chatContent
    if(splitMessage[0]==='link'&&message.includes('|')&&splitMessage.length===2){
        chatContent = document.createElement('a');
        chatContent.href=splitMessage[1]
        chatContent.target="_blank"
        chatContent.textContent="media link"
    }
    else {
        chatContent = document.createElement('div');
        chatContent.textContent = `${message}`

        chatContent.style.borderRadius = "5px"
    }
    if(sender1===sender){
        // chat.style.marginLeft='100px'
        chat.appendChild(chatContent)
        chat.style.display="flex"
        chat.style.justifyContent="flex-end"
        chat.style.alignContent="center";
        chatContent.style.backgroundColor="#19443e"
        chatContent.style.padding="10px 20px 10px 10px"

    }

    else{
        chat.appendChild(chatContent)
        chat.style.display="flex"
        chat.style.justifyContent="flex-start"
        chat.style.alignContent="center";
        chat.style.backgroundColor="#"
        chatContent.style.backgroundColor="#4e5352"
        chatContent.style.padding="10px 10px 10px 20px"
    }

    chatBox.appendChild(chat)
}



export const loadOldChats=async ()=>{

    document.querySelector(".chatBox").textContent=''
    let id1=sender+','+receiver;
    let id2=receiver+','+sender;

    let chats=await fetch(`/api/chats/${id1}`);
    let actualChats=null
    if(chats.status!=404){
        actualChats=await chats.json()

    }
    else{
        chats=await fetch(`/api/chats/${id2}`);
        if(chats.status!=404){
            actualChats=await chats.json()

        }
    }
    addProfileHeader(receiver)
    if(actualChats!=null) {
        actualChats.forEach((chat) => {
            addChat(chat.sender, chat.receiver,chat.message)
        })
    }
}

export const loadFriends= async ()=>{

    let response=await fetch(`/api/friends/${sender}`)

    const friendBox=document.querySelector('.friendBox')
    if(response.ok){
        const friendsInDb = await response.json()

        friendsInDb.forEach(friend => {
            if(!friends.includes(friend)){
                friends.push(friend)
                addFriend(friend)
            }

        })


    }
}

export const fileSubmit=async (formData)=>{

    formData.append("sender",sender);
    formData.append("receiver",receiver);
    const response=await fetch("/api/files",{
        method:"POST",
        body:formData
    })

    const content=await response.json()

    //got the link?
    addChat(sender,receiver,content.message)


}
