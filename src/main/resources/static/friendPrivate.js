import {stompClient} from "./webSocketPrivate.js"
import {loadOldChats,setCurrentReceiver} from "./chatPrivate.js";

let friends=[]
export const makeEachOtherFriends=(sender,receiver)=>{
    if(receiver===""||receiver===null||receiver===sender){
        return
    }


    const firstObj={
        "friendUsername":receiver,
        "userUsername":sender
    }

    const secondObj={
        "friendUsername":sender,
        "userUsername":receiver
    }
    document.querySelector(".receiver").value=""



    stompClient.publish({
        destination:`/app/message/addFriends`,
        body:JSON.stringify(firstObj)
    })
    stompClient.publish({
        destination:`/app/message/addFriends`,
        body:JSON.stringify(secondObj)
    })


}
export const addFriend= (friend)=>{
    let name=friend
    const friendDiv=document.createElement('div')
    friendDiv.textContent=name
    friendDiv.className="friendDiv"
    friendDiv.addEventListener('click',async (e)=>{
        e.preventDefault()
        setCurrentReceiver(e.target.textContent)
        await loadOldChats();
        document.querySelector(".messageForm").hidden=false
        document.querySelector(".fileForm").hidden=false
    })
    const friendBox=document.querySelector('.friendBox')
    friendBox.appendChild(friendDiv)
}

