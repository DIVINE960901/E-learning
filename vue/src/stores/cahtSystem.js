import { defineStore } from "pinia";
//import useChatInfoStore from "@/stores/chatInfo.js"
//const chatInfoTo = useChatInfoStore()
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket',
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
});
const chatSystem = defineStore('chatSystem',()=>{
    const initConnect = (userId)=>{
        stompClient.onConnect = ()=>{
            var destination = '/user/' + userId + '/queue/greetings';
            stompClient.subscribe(destination, (greeting) => {
                let currentMessage = JSON.parse(greeting.body).message;
                const getSendId = JSON.parse(greeting.body).senderId;
                let chatFound = true;
                for (let i = 0; i < chatInfoTo.info.length; i++) {
                    if (chatInfoTo.info[i].id === getSendId) {
                        console.log('已找到与 localStorage:' + chatInfoTo.info[i].id);
                        chatFound = false
                        break
                    }
                }
        
                if (chatFound) {
                    console.log('未找到与 localStorage:' + getSendId);
                    const newChat = {
                        title: '与' + JSON.parse(greeting.body).sender + '的聊天',
                        id: getSendId
                    };
                    chatInfoTo.setInfo(newChat);
                    console.log(chatInfoTo.info);
                }       
        
                var chatData = sessionStorage.getItem(getSendId)
                if (chatData != null) {
                    sessionChatDataList.value = JSON.parse(chatData);
                    sessionChatDataList.value.push({
                        source: getSendId,
                        message: currentMessage
                    })
                    sessionStorage.setItem(getSendId, JSON.stringify(sessionChatDataList.value));
                    console.log('有');
                    sessionChatDataList.value = [];
                } else {
                    sessionChatDataList.value.push({
                        source: getSendId,
                        message: currentMessage
                    })
                    sessionStorage.setItem(getSendId, JSON.stringify(sessionChatDataList.value));
                    console.log('没有');
                    sessionChatDataList.value = [];
                }
                console.log(JSON.parse(greeting.body).senderId);
                ElMessage.success('收到来自' + JSON.parse(greeting.body).sender + '消息')
            });
        }
    }
    const connect = () => {
        stompClient.activate();
        console.log("Connected");
    }
    const disconnect = () => {
        stompClient.deactivate();
        console.log("Disconnected");
    
    };
    return{
        initConnect,
        connect,
        disconnect
    }
},{persists:true})

export default chatSystem;