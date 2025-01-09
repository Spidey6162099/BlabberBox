export const addProfileHeader=(friend)=>{
    const profileHolder=document.createElement('div')
    profileHolder.className="profileHolder"
    profileHolder.style.display="flex"
    profileHolder.style.justifyContent="center"
    profileHolder.style.alignItems="center"
    profileHolder.style.flexDirection="column"


    const chatProfile=document.createElement('div')
    chatProfile.className="chatProfile"
    chatProfile.textContent=friend[0].toUpperCase()
    chatProfile.style.borderRadius="50%"
    chatProfile.style.border="2px red"
    chatProfile.style.backgroundColor="#585858"
    chatProfile.style.color="#F5F5F5"

    const friendName=document.createElement('div')
    friendName.style.color="#F5F5F5"
    friendName.className="friendName"
    friendName.textContent=friend

    const chatBox=document.querySelector(".chatBox");
    profileHolder.appendChild(chatProfile)
    profileHolder.appendChild(friendName)
    chatBox.appendChild(profileHolder)
}