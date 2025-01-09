import {stompClient,initializeStompConnection} from "./webSocketPrivate.js";
import {makeEachOtherFriends,addFriend} from "./friendPrivate.js";
import {addChat,setCurrentSender,setCurrentReceiver,sendMessagePrivate,fileSubmit} from "./chatPrivate.js";
import {loadFriends} from "./chatPrivate.js";

let sender=null
let receiver=null
let friends=[]
// let reciever=null


//connect button
const response=await fetch("/user")
const data=await response.json()
const senderRetrieved=data.user

    sender=senderRetrieved

    //first extract the sender
    await initializeStompConnection(sender)
    // document.querySelector(".connectForm").hidden=true
    document.querySelector(".receiverForm").hidden=false
    document.querySelector(".contentBox")
    await loadFriends()




// setting up reciever
const receiverBtn=document.querySelector(".receiverSubmit")
receiverBtn.addEventListener('click',async (e)=>{
    e.preventDefault()
    let receiverName=document.querySelector(".receiver").value
    if(receiverName===""||receiverName===null||receiverName===sender){
        return
    }

    receiver=receiverName
    makeEachOtherFriends(sender,receiver)

})

//actual message button
const submitBtn=document.querySelector(".messageSubmit")
const onSubmit=async ()=>{


    const message=document.querySelector(".message").value
    // const filesInput=document.querySelector(".mediaInput")
    //
    // Array.from(filesInput.files).forEach(file=>{
    //     const fileReader=new FileReader()
    //     const currFile=file
    //     reader.onload((e)=>{
    //         let rewData=new ArrayBuffer()
    //         rewData=e.target.result
    //     })
    // })


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


const fileForm=document.querySelector(".fileForm")
fileForm.addEventListener('submit',async (e)=>{
    e.preventDefault()

    const formData=new FormData(fileForm)
    console.log(formData.get("file"))

        await fileSubmit(formData)

})

