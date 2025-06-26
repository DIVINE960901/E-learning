import {  ref } from 'vue';


import { ElMessage } from 'element-plus';


//console.log(chatInfoTo.info);
const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});
export const initChat = stompClient.onConnect = (frame) => {
  console.log('Connected: ' + frame);
  var destination = '/user/' + userInfoStore.info.id + '/queue/greetings';
  stompClient.subscribe(destination, (greeting) => {
    //console.log(greeting.body);
    let currentMessage = JSON.parse(greeting.body).message;
    const getSendId = JSON.parse(greeting.body).senderId;
    /**
     * 获取chatInfoTo.info里的聊天对象 for(let a in chatInfoTo.info) {chatInfoTo.info[a] == getSendId}
     * 如果为null，说明聊天是新的，需要添加到聊天列表里 chatInfoTo.set
     * const newChat = {
     *  title: '与'+JSON.parse(greeting.body).sender+'的聊天'
     *  id: getSendId
     * }
     * chatInfoTo.setInfo(newChat)
    */
    //和目标用户的聊天
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
      //editableTabs.value = [...chatInfoTo.info];
    }



    //获取sessionStorage里的聊天记录
    var chatData = sessionStorage.getItem(getSendId)
    if (chatData != null) {
      //console.log(chatData);
      //console.log(JSON.parse(chatData));
      sessionChatDataList.value = JSON.parse(chatData);
      //console.log(sessionChatDataList.value);
      sessionChatDataList.value.push({
        source: getSendId,
        message: currentMessage
      })
      //console.log(sessionChatDataList.value);
      //更新messageList
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
    //判断当前聊天对象，若与发送者相同则渲染，否则不做任何操作
    console.log(JSON.parse(greeting.body).senderId);
    // if (editableTabsValue.value === JSON.parse(greeting.body).senderId) {
    //   console.log('与发送者相同');
      //messList.value.push(messageFrom(currentMessage));
    // } else {
      ElMessage.success('收到来自' + JSON.parse(greeting.body).sender + '消息')
    //}

    console.log(messList.value);
    //getReceiver.value = JSON.parse(greeting.body).senderId;
    //console.log(getReceiver.value);

    /**
     * sessionStorage
     * var chatData = sessionStorage.getItem(getReceiver)
     * if(chatData != null){
     *  //将聊天记录渲染到聊天区
     * }
     */
    //messList.value.push(messageTo());
    //showGreeting(JSON.parse(greeting.body).message);
    // showSender(JSON.parse(greeting.body).sender);
    // showReceiver(JSON.parse(greeting.body).receiver);
  });
};
export const connect = () => {
    stompClient.activate();
    console.log("Connected");
    //console.log(getReceiver.value);
  }

export const disconnect = () => {
  stompClient.deactivate();
  console.log("Disconnected");
  //console.log("Disconnected");

};
