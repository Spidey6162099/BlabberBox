
const stompClient=new StompJs.Client({
    brokerURL: 'ws://localhost:8081/chat'
})


let username=null
let profileColor=null
const displayMessage=(message)=>{
    const chat=document.querySelector(".chatBox")
    const newContent=document.createElement('p');
    const profile=document.createElement('p');


    profile.textContent=message.sender[0]
    profile.style.backgroundColor=message.color
    profile.className="profile"



    newContent.textContent=`${message.sender} : ${message.message}`

    const chatContainer=document.createElement("div")
    chatContainer.className="chatContainer"

    chatContainer.appendChild(profile)
    chatContainer.appendChild(newContent)

    chat.appendChild(chatContainer)
}
stompClient.onConnect=(frame)=>{
    console.log('Connected: ' + frame);

    const newObj={sender:username,message: "",color:profileColor}
    stompClient.publish({
        destination:"/app/addUser",
        body:JSON.stringify(newObj)
    })


    stompClient.subscribe("/topics/hello",(message)=>{
        console.log("I have recieved it yay")
        const parsedMessage=JSON.parse(message.body)
        console.log(parsedMessage)

        displayMessage(parsedMessage)
    })

}
const sendMessage=(message)=>{

    stompClient.publish({
        destination:"/app/send",
        body:JSON.stringify(message)
    })
}



const connect=()=>{
        usernameVal=document.querySelector("#name").value
        document.querySelector("#name").value=""
        if(usernameVal){
            username=usernameVal
        }

    if(profileColor===null){
        profileColors=["violet","indigo","blue","green","yellow","orange","red"]
        profileColor=profileColors[Math.floor(Math.random()*profileColors.length)]

    }

    stompClient.activate()


    document.querySelector(".form2").hidden=true
    document.querySelector(".form").hidden=false
}

const disconnect=()=>{
    stompClient.deactivate()
    // setConnection(false)
    document.querySelector(".form").hidden=true
    document.querySelector(".form2").hidden=false
    document.querySelector(".chatBox").replaceChildren();
}




const submitBtn=document.querySelector("#submit");

submitBtn.addEventListener("click",(event)=>{
    event.preventDefault()
    // const sender=document.querySelector(".sender").value
    // const recipient=document.querySelector(".recipient").value
    const message=document.querySelector("#message");


    const messageObj={sender:username,message:message.value,color:profileColor}
    message.value=""
    sendMessage(messageObj)

})

const connectBtn=document.querySelector(".connect")
connectBtn.addEventListener('click',(e)=>{
    e.preventDefault()

    connect()



})

const disconnectBtn=document.querySelector(".disconnect")
disconnectBtn.addEventListener('click',(e)=>{
    e.preventDefault()
    disconnect()

})






const setConnection=(val)=>{
    const connectBtn=document.querySelector(".connect")
    const disconnectBtn=document.querySelector(".disconnect")
    connectBtn.disabled=val===true?"disabled":""
    disconnectBtn.disabled=val===true?"":"disabled"
}
