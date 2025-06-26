<script setup>
import { useTokenStore } from '@/stores/counter.js'
import { userInfoService } from "@/api/user.js"
import useUserInfoStore from "@/stores/userinfo.js"
import useChatInfoStore from "@/stores/chatInfo.js"
import chatSystem from '@/stores/cahtSystem'
import { ref, reactive } from 'vue'
import { Search, User, Message } from '@element-plus/icons-vue'
import { userRegisterService, userLoginService } from '../api/user.js'
import { jwtDecode } from "jwt-decode";
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
const router = useRouter();
const tokenStore = useTokenStore()
const useChatSystem = chatSystem()
// const isAccountValid = ref(true)
const input = ref('')
const loginVisible = ref(false)
const registerVisible = ref(false)
const activeIndex = ref('')
const userInfoStore = useUserInfoStore()
const chatInfoTo = useChatInfoStore()
const sessionChatDataList = ref([])

const chatID = ref('')

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket',
    connectHeaders:{
        login: chatID.value
    },
    heartbeatIncoming: 4000,
    heartbeatOutgoing: 4000,
});
const FromData = reactive({
    username: '',
    password: '',
    Repassword: '',
})

stompClient.onConnect = (frame) => {
    console.log('Connected:' + frame);
    //console.log(frame.headers);
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
        // ElNotification({
        //     title:'您收到了新消息',
        //     message:'收到来自' + JSON.parse(greeting.body).sender + '消息'
        // })
        //console.log(messList.value);
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
const connect = () => {
    stompClient.activate();
    console.log("Connected");
    //console.log(getReceiver.value);
}
// const disconnectHeaders = {
//     login: userInfoStore.info.id
// }

// stompClient.onDisconnect(disconnectHeaders)
const disconnect = () => {
    stompClient.deactivate();
    console.log("Disconnected");
    //console.log("Disconnected");

};
// window.addEventListener('beforeunload',function(event){
//     const disconnectHeaders = {
//         login: userInfoStore.info.id,
//     }
//     stompClient.deactivate(disconnectHeaders);
// })
const isHasToken = () => {
    const token = tokenStore.token;
    try {
        const decodedToken = jwtDecode(token);
        const now = Math.floor(Date.now() / 1000);
        const isTokenExpired = decodedToken.exp < now;
        return !isTokenExpired;
    } catch (error) {
        console.error('Error decoding JWT token:', error);
        return false;
    }
};

const rePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('输入密码为空'))
    } else if (value !== FromData.password) {
        callback(new Error('两次密码不一致'))
    } else {
        callback()
    }
}
const Rules = reactive({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],

    Repassword: [{ validator: rePassword, trigger: 'blur' }],

})

const registerdDate = async () => {
    let result = await userRegisterService(FromData)
    ElMessage.success(result.message ? result.message : '注册成功')
    //alert(result.msg ? result.msg : '注册成功')
    registerVisible.value = false

}

const loginData = async () => {
    let result = await userLoginService(FromData)
    loginVisible.value = false
    ElMessage.success(result.message ? result.message : '登录成功')
    tokenStore.setToken(result.data)
    //alert(result.msg ? result.msg : '登录成功')
    //router.push({ name: 'home' });
    getUserInfo()
    isHasToken()
    //window.location.reload()
}


const getUserInfo = async () => {
    let result = await userInfoService()
    userInfoStore.setInfo(result.data)
    //console.log(userInfoStore.info.id);
    //chatID = userInfoStore.info.id
    connect()
}
getUserInfo()
//console.log(userInfoStore.info.id);

// console.log(userInfoStore.info.userPic);
// console.log(userInfoStore.info.identity);
//console.log(userInfoStore.info.nickname);
const skipToPersonalCenter = () => {
    router.push('/personPage')
}

const skipToChatRoom = () => {
    disconnect()
    router.push('/chatSystem')
}

const LogOut = () => {
    userInfoStore.removeInfo()
    tokenStore.removeToken()
    console.log(userInfoStore.info);
    console.log(tokenStore.token);
    router.push({ name: 'home' });
    disconnect()
}

const clearFrom = () => {
    FromData.username = ''
    FromData.password = ''
    FromData.Repassword = ''
}

const searchTitle = (TargetName) => {
    // console.log(TargetName);
    router.push({ name: 'search', params: { title: TargetName } });
    input.value = ''
}

</script>
<template>
    <div class="header-content">
        <div class="header-center">
            <div class="logo-box">
                <img src="/images/tag1.jpg" />
            </div>
            <div class="header-menu">
                <el-menu :default-active="activeIndex" mode="horizontal" style="width:400px;border-bottom: solid 0px" >
                    <el-menu-item index="1" class="menu-item" style="margin-left: 10px;" >
                        <!-- <span class="title">首页</span> -->
                        <a href="http://localhost:5173" class="title" style="color: rgba(115, 115, 115, 1);">首页</a>
                    </el-menu-item>
                    <el-menu-item index="2" class="menu-item" style="margin-left: 10px;" >
                        <!-- <span class="title">课程</span> -->
                        <a href="http://localhost:5173/allcourse" class="title"
                            style="color: rgba(115, 115, 115, 1);">课程</a>
                    </el-menu-item>
                    <el-menu-item index="3" class="menu-item" style="margin-left: 10px;" >
                        <!-- <span class="title">题库</span> -->
                        <a href="http://localhost:5173/testlist" class="title"
                            style="color: rgba(115, 115, 115, 1);">题库</a>
                    </el-menu-item>
                </el-menu>
            </div>
            <div class="search">
                <el-input v-model="input" placeholder="请输入内容" :prefix-icon="Search" size="large" autosize />
            </div>
            <div class="search-button">
                <el-button type="success" size="large" @click="searchTitle(input)">搜索</el-button>
            </div>
            <div class="User-icon" v-if="isHasToken()">
                <el-dropdown trigger="click">
                    <div class="block" style="margin-right: 10px;">
                        <el-avatar :size="40" :src="userInfoStore.info.userPic" />
                    </div>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item @click="skipToPersonalCenter()">
                                <el-icon>
                                    <User />
                                </el-icon>
                                {{ userInfoStore.info.nickname }}的个人中心
                            </el-dropdown-item>
                            <el-dropdown-item @click="skipToChatRoom()">
                                <el-icon>
                                    <Message />
                                </el-icon>
                                私信
                            </el-dropdown-item>
                            <el-dropdown-item @click="LogOut()">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </div>
            <div class="rl-button" v-else>
                <div class="login-button">
                    <el-button type="success" round size="large" @click="loginVisible = true">登录</el-button>
                </div>
                <div class="register-button">
                    <el-button round size="large" @click="registerVisible = true">注册</el-button>
                </div>
            </div>

        </div>
    </div>
    <el-dialog title="login" v-model="loginVisible" :before-close="clearFrom()" center width="300px">
        <el-form :model="FromData" :rules="Rules" label-position="top" hide-required-asterisk="true">
            <el-form-item label="账号" prop="username">
                <el-input v-model="FromData.username" placeholder="请输入账号" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="FromData.password" placeholder="请输入密码" type="password" show-password />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="loginVisible = false; clearFrom()">取消</el-button>
                <el-button type="primary" @click="() => { loginData(); clearFrom() }">
                    登录
                </el-button>
            </span>
        </template>
    </el-dialog>
    <el-dialog title="register" v-model="registerVisible" :before-close="clearFrom()" center width="300px">
        <el-form :model="FromData" :rules="Rules" label-position="top" hide-required-asterisk="true">
            <el-form-item label="账号" prop="username">
                <el-input v-model="FromData.username" placeholder="请输入账号" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="FromData.password" placeholder="请输入密码" type="password" show-password />
            </el-form-item>
            <el-form-item label="再次输入密码" prop="Repassword">
                <el-input v-model="FromData.Repassword" placeholder="请再次输入密码" type="password" show-password />
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="registerVisible = false; clearFrom()">取消</el-button>
                <el-button type="primary" @click="() => { registerdDate(); clearFrom() }">
                    注册
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>
<style scoped>
div {
    display: block;
}


.logo-box {
    margin: 10px 20px 0px 20px;
    align-self: center;
    height: 65px;
    padding-left: 15px;
    float: left;
}

.header-menu {
    width: 365px;
    height: 65px;
    float: left;
}

.menu-item {
    width: 100px;
    height: 66px;
    display: flex;
    justify-content: center;
    align-self: center;
    padding: 16px 12px 7px;
    background-repeat: no-repeat;
    background-position: 0px 0px;
    background-size: 100% 100%;
    background-origin: padding-box;

}

.title {
    font-family: AlibabaPuHuiTi-Regular;
    font-size: 26px;
    line-height: 43px;
    margin-right: 1px;
    align-self: flex-start;
    max-width: 87px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    padding-right: 5px;
    text-decoration: none;
}

.search {
    padding: 15px 10px 0px 0px;
    float: left;
}

.search-button {
    float: left;
    padding: 15px 0px 0px 10px;

}

.rl-button {
    padding-top: 15px;
    float: right;
}

.User-icon {
    padding-top: 15px;
    float: right;
    margin-right: 50px;
}

.login-button,
.register-button {
    padding-left: 30px;
    float: left;
}

.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>