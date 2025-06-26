<script setup>
import { h, ref,onUnmounted } from 'vue';
import messageFromBone from '@/components/messageFromBone.vue'
import messageToBone from '@/components/messageToBone.vue';
import useUserInfoStore from "@/stores/userinfo.js"
import useChatInfoStore from "@/stores/chatInfo.js"
import { ElMessage } from 'element-plus';
import router from '@/router';
const userInfoStore = useUserInfoStore()
const chatInfoTo = useChatInfoStore()
const getReceiver = ref(chatInfoTo.chatToInfo)
const getReceiverNickname = ref(chatInfoTo.chatToNickname)
const editableTabs = ref(chatInfoTo.info)
const editableTabsValue = ref()
const sessionChatDataList = ref([])

//console.log(chatInfoTo.info);
const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/gs-guide-websocket',
  connectHeaders:{
        login: userInfoStore.info.id
    },
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
});
stompClient.onConnect = (frame) => {
  console.log('Connected: ' + frame);
  var destination = '/user/' + userInfoStore.info.id + '/queue/greetings';
  stompClient.subscribe(destination, (greeting) => {
    //console.log(greeting.headers);
    console.log(greeting.body);
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
      editableTabs.value = [...chatInfoTo.info];
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
    if (editableTabsValue.value === JSON.parse(greeting.body).senderId) {
      console.log('与发送者相同');
      messList.value.push(messageFrom(currentMessage));
    } else {
      ElMessage.success('收到来自' + JSON.parse(greeting.body).sender + '消息')
    }

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

stompClient.onWebSocketError = (error) => {
  console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
  console.error('Broker reported error: ' + frame.headers['message']);
  console.error('Additional details: ' + frame.body);
  ElMessage.error('用户不在线');
};
const connect = () => {
  stompClient.activate();
  console.log("Connected");
  //console.log(getReceiver.value);
}
connect()
const disconnect = () => {
  stompClient.deactivate();
  //console.log("Disconnected");
  messList.value = [];
};
// window.addEventListener('beforeunload',function(event){
//     stompClient.deactivate();
// })

const messList = ref([]);
const textarea = ref('');
// let currentMessage = '';


const messageTo = (TomessageData) => h(messageToBone, { type: 'to', message: TomessageData });
const messageFrom = (FromMessageData) => h(messageFromBone, { type: 'from', message: FromMessageData });

// const checkNode = () => {

//   messList.value.push(messageTo());
//   textarea.value = '';

// };

// const getMessage = () => {

//   messList.value.push(messageFrom());
//   textarea.value = '';

// };
function sendName() {
  console.log(getReceiver.value);
  stompClient.publish({
    destination: "/app/sendUser",
    body: JSON.stringify({ 'SenderId': userInfoStore.info.id, 'message': textarea.value, 'sender': userInfoStore.info.nickname, 'receiver': getReceiver.value })
  });
  //console.log(stompClient);
  messList.value.push(messageTo(textarea.value));
  //console.log(getReceiverNickname.value);
  //console.log(JSON.parse(chatData));
  //var chatData = sessionStorage.getItem(getSendId)


  //和目标用户的聊天
  let chatFound = true;
  for (let i = 0; i < chatInfoTo.info.length; i++) {
    if (chatInfoTo.info[i].id === getReceiver.value) {
      console.log('已找到与 localStorage:' + chatInfoTo.info[i].id);
      chatFound = false
      break
    }
  }

  if (chatFound) {
    console.log('未找到与 localStorage:' + getReceiver.value);
    const newChat = {
      title: '与' + getReceiverNickname.value + '的聊天',
      id: getReceiver.value
    };
    chatInfoTo.setInfo(newChat);
    console.log(chatInfoTo.info);
    editableTabs.value = [...chatInfoTo.info];
  }


  var chatData = sessionStorage.getItem(getReceiver.value)
  if (chatData != null) {
    sessionChatDataList.value = JSON.parse(chatData);
    sessionChatDataList.value.push({
      source: userInfoStore.info.id,
      message: textarea.value
    })
    //更新messageList
    sessionStorage.setItem(getReceiver.value, JSON.stringify(sessionChatDataList.value));
    console.log('有');
    sessionChatDataList.value = [];
  } else {
    sessionChatDataList.value.push({
      source: userInfoStore.info.id,
      message: textarea.value
    })
    sessionStorage.setItem(getReceiver.value, JSON.stringify(sessionChatDataList.value));
    console.log('没有');
    sessionChatDataList.value = [];
  }
  textarea.value = '';
  /**
   * var chatData = sessionStorage.getItem(getReceiver.value)
   * if(chatData != null){
   *  chatData.push(messageFrom());
   * }
   * //当当前聊天的用户等于数据传输的用户时，存入sessionStorage
   * sessionStorage.setItem(getReceiver.value,chatData);
   */
}




const changeChatRoom = (id) => {
  // console.log('changeChatRoom');
  messList.value = [];
  getReceiver.value = id;

  console.log(getReceiver.value);
  // console.log(id)
  var chatData = sessionStorage.getItem(id)
  if (chatData != null) {
    console.log(JSON.parse(chatData));
    // console.log(chatData);
    // messList.value = JSON.parse(chatData)
    for (let c of JSON.parse(chatData)) {
      if (c.source === userInfoStore.info.id) {
        messList.value.push(messageTo(c.message));

      } else {
        messList.value.push(messageFrom(c.message));
      }
    }
    console.log(messList.value);
  }
}


const removeChat = (targetId) => {
  // let activate = id.value
  // console.log(activate);
  // console.log(targetId);
  const indexToRemove = editableTabs.value.findIndex(tab => tab.id === targetId);

  if (indexToRemove !== -1) {
    editableTabs.value.splice(indexToRemove, 1);
    if (editableTabsValue.value === targetId) {
      const nextTab = editableTabs.value[indexToRemove > 0 ? indexToRemove - 1 : 0];
      if (nextTab) {
        editableTabsValue.value = nextTab.id;
      } else if (editableTabs.value.length > 0) {
        editableTabsValue.value = editableTabs.value[0].id;
      } else {
        editableTabsValue.value = '';
      }
    }
    //console.log(editableTabs.value);
    sessionStorage.removeItem(targetId);
    const removedChat = editableTabs.value.find(tab => tab.id === targetId);
    if (removedChat) {
      chatInfoTo.removeChat(targetId);
    }
    // chatInfoTo.removeInfo(editableTabs.value.map(tab => ({ id: tab.id, title: tab.title })));
  }
  messList.value = [];
}
const goBack = () => {
  disconnect();
  router.back();
}
</script>

<template>
  <div class="chatContainer">
    <div class="common-layout">
      <el-container style="width: 990px;">
        <el-header width="100%" height="60px">
          <el-page-header @back="goBack()">
            <template #content>
              <span> 用户名: {{ userInfoStore.info.nickname }}</span>
            </template>
          </el-page-header>
        </el-header>
        <el-container>
          <el-aside style="width: 265px;">
            <el-tabs v-model="editableTabsValue" tab-position="right" type="card" class="demo-tabs" closable
              @tab-remove="(targetId) => removeChat(targetId)" @tab-change="changeChatRoom(editableTabsValue)">
              <el-tab-pane v-for="item in editableTabs" :key="item.id" :label="item.title" :name="item.id">
              </el-tab-pane>
            </el-tabs>
          </el-aside>
          <el-container>
            <el-main style="height:600px;">
              <el-scrollbar>

                <div class="messageList">
                  <el-space wrap size="large" direction="vertical">
                    <!-- 直接在这里渲染messageBone组件 -->
                    <component v-for="(item, index) in messList" :key="index" :is="item" />
                  </el-space>
                </div>
              </el-scrollbar>
            </el-main>
            <el-footer style="height: 200px;">
              <el-input v-model="textarea" style="width: 680px; margin-top: 20px;" :rows="5" resize="none"
                type="textarea" placeholder="Please input" />
              <el-button type="primary" style="float: right; margin-top: 10px; margin-right: 5px;" size="large"
                @click="sendName()">发送</el-button>
              <!-- <el-button @click="">connect</el-button>
              <el-button @click="">disconnect</el-button> -->
            </el-footer>
          </el-container>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<style>
.chatContainer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 90vh;
  min-height: 100%;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.common-layout {
  width: 990px;
  background-color: transparent;
}

.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  background-color: cadetblue;
  color: #333;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

.el-footer {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-page-header {
  height: 100%;
  display: flex;
  align-items: center;
}

.el-page-header>.el-page-header__content {
  flex: 1;
}
</style>