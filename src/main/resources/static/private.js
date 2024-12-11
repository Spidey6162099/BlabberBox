const stompClient=new StompJs.Client({
    brokerURL: 'ws://localhost:8081/chat'
})
let sender=null
let receiver=null
let friends=[]
// let reciever=null
stompClient.onConnect=(frame)=>{
    sender=document.querySelector(".sender").value

    stompClient.publish({
        "destination":"/app/addPrivateUser",
        "body":sender})

    console.log('Connected: ' + frame);
    stompClient.subscribe(`/queue/reciever/${sender}`,(message)=>{
        const parsedMessage=JSON.parse(message.body)

        console.log("message: "+parsedMessage.message+" from"+parsedMessage.sender);
        if(receiver!=null&&receiver===parsedMessage.sender) {
            addChat(parsedMessage.sender, parsedMessage.receiver, parsedMessage.message)
        }
    });

    stompClient.subscribe(`/queue/friends/${sender}`,(message)=>{

        const parsedMessage=message.body
        console.log("works"+parsedMessage)
        //we have the name of the friend now we can safely add it

            friends.push(parsedMessage)
            addFriend(parsedMessage)



    })

}


//connect button
const connectBtn=document.querySelector(".connect");

const onConnect=()=>{
    //first extract the sender
    sender=document.querySelector(".sender").value
    stompClient.activate()

    document.querySelector(".connectForm").hidden=true
    document.querySelector(".receiverForm").hidden=false
    document.querySelector(".contentBox")

}
connectBtn.addEventListener("click",async (e)=>{
    e.preventDefault()
    onConnect()
    await loadFriends()
})

// setting up reciever
const receiverBtn=document.querySelector(".receiverSubmit")
receiverBtn.addEventListener('click',async (e)=>{
    e.preventDefault()
    let receiverName=document.querySelector(".receiver").value
    if(receiverName===""||receiverName===null||receiverName===sender){
        return
    }


    const firstObj={
        "friendUsername":receiverName,
        "userUsername":sender
    }

    const secondObj={
        "friendUsername":sender,
        "userUsername":receiverName
    }
    document.querySelector(".receiver").value=""



    stompClient.publish({
        destination:`/app/addFriends`,
        body:JSON.stringify(firstObj)
    })
    stompClient.publish({
        destination:`/app/addFriends`,
        body:JSON.stringify(secondObj)
    })


})

//actual message button
const submitBtn=document.querySelector(".messageSubmit")
const onSubmit=async ()=>{


    const message=document.querySelector(".message").value
    // const receiver=document.querySelector(".receiver").value
    const newObj={content:message}
    //now we have both the sender and receiver

    sendMessagePrivate(newObj)
    addChat(sender,receiver,message)
    document.querySelector(".message").value=''

}
submitBtn.addEventListener('click',(e)=>{
    e.preventDefault()
    onSubmit()
})


const addChat=(sender1,receiver1,message)=>{
    //assume three details for now , sender ,receiver and message
    const chatBox=document.querySelector(".chatBox");
    const chat=document.createElement('div')
    chat.style.padding="10px"
    chat.className="chat"
    // const chatProfile=document.createElement('div')
    // chatProfile.className='chatProfile'
    // chatProfile.textContent=sender1[0].toUpperCase()
    // chatProfile.style.display="flex"
    // chatProfile.style.justifyContent='center'
    // chatProfile.style.alignContent='center'
    // // chatProfile.style.fontSize="20px"
    // chatProfile.style.borderRadius="50%"
    // chatProfile.style.border="purple solid 2px"
    // chatProfile.style.padding="5px";


    const chatContent=document.createElement('div');
    chatContent.textContent=`${message}`

    chatContent.style.borderRadius="5px"

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
//sending message
const sendMessagePrivate=(message)=>{
    const newPrivateMessage={
        "sender":sender,
        "message":message.content,
        "receiver":receiver
    }


    stompClient.publish({

        destination:"/app/sendPrivate",
        body:JSON.stringify(newPrivateMessage)
    })
}

const loadOldChats=async ()=>{
    document.querySelector(".chatBox").textContent=''
    id1=sender+','+receiver;
    id2=receiver+','+sender;

    let chats=await fetch(`/chats/${id1}`);
    let actualChats=null
    if(chats.status!=404){
        actualChats=await chats.json()

    }
    else{
        chats=await fetch(`/chats/${id2}`);
        if(chats.status!=404){
            actualChats=await chats.json()
            console.log(actualChats)
        }
    }

    if(actualChats!=null) {
        actualChats.forEach((chat) => {
            addChat(chat.sender, chat.receiver,chat.message)
        })
    }
}

//add the friend to the chat list
const addFriend= (friend)=>{
    let name=friend
    const friendDiv=document.createElement('div')
    friendDiv.textContent=name
    friendDiv.className="friendDiv"
    friendDiv.addEventListener('click',async (e)=>{
        e.preventDefault()
        receiver=e.target.textContent;
        await loadOldChats();
        document.querySelector(".messageForm").hidden=false
    })
    const friendBox=document.querySelector('.friendBox')
    friendBox.appendChild(friendDiv)
}

//loads all users in the friendlist of signed in user
const loadFriends= async ()=>{

    let response=await fetch(`/friends/${sender}`)

    const friendBox=document.querySelector('.friendBox')
    if(response.ok){
        const friendsInDb = await response.json()


                console.log("I am here the friends is not null")
                friendsInDb.forEach(friend => {
                    if(!friends.includes(friend)){
                        friends.push(friend)
                        addFriend(friend)
                    }

                })


    }
}

// setInterval(async ()=>{
//     if(sender!=null){
//         await loadFriends()
//     }
//
//
// },5000)