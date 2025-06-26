import { defineStore } from "pinia";
import { ref } from "vue";
const useChatInfoStore = defineStore('chatInfo',()=>{
    const info = ref([{
        title:'站内私信',
        id:'topic'
    }])
    const chatToInfo = ref({})
    const chatToNickname = ref({})
    const setchatToNickname = (newInfo)=>{
        chatToNickname.value = newInfo
    }
    const removeToNickname = ()=>{
        chatToNickname.value = {}
    }
    const setChatToInfo = (newInfo)=>{
        chatToInfo.value = newInfo
    }
    const removeToInfo = ()=>{
        chatToInfo.value = {}
    }
    const setInfo = (newChatOrChats) => {
        info.value.push(newChatOrChats)
    };
    const removeInfo = (targetId)=>{
        this.info = this.info.filter(chat => chat.id !== targetId);
    }

    return {
        info,
        chatToInfo,
        chatToNickname,
        setchatToNickname,
        removeToNickname,
        setInfo,
        removeInfo,
        setChatToInfo,
        removeToInfo,
    }
},{persists:true})

export default useChatInfoStore