<template>
    <el-container style="max-width: 1500px; margin: auto;">
        <el-header>
            <headpart />
        </el-header>
        <el-main style="margin-top: 20px; height: auto;">
            <div class="primary">
                <el-container style="background-color: #f5f7fa;">
                    <el-aside style="background-color: #ebeef5; width: 200px;height: 800px;">
                        <div class="aside-div">
                            <div class="icon">
                                <el-avatar :size="88" :src="userInfoStore.info.userPic?userInfoStore.info.userPic:testImg" style="padding-left: 0px;" />
                            </div>
                            <div class="menuset-div">
                                <el-menu active-text-color="#ffd04b" background-color="#545c64"
                                    class="el-menu-vertical-demo" default-active="2" text-color="#fff"
                                    @select="handleSelect">
                                    <el-sub-menu index="个人资料">
                                        <template #title>
                                            <span>个人资料</span>
                                        </template>
                                        <el-menu-item-group>
                                            <el-menu-item index="PersonContainer">修改信息</el-menu-item>
                                            <el-menu-item index="changepassword">修改密码</el-menu-item>
                                        </el-menu-item-group>
                                    </el-sub-menu>
                                    <el-menu-item index="Subscribe">
                                        <span>课程收藏</span>
                                    </el-menu-item>
                                    <el-menu-item index="history">
                                        <span>历史记录</span>
                                    </el-menu-item>
                                    <el-menu-item index="uploadCourse" v-if="isIdentity()">
                                        <span>课程管理</span>
                                    </el-menu-item>
                                    <el-menu-item index="examUpload" v-if="isIdentity()">
                                        <span>题库管理</span>
                                    </el-menu-item>
                                </el-menu>
                            </div>
                        </div>
                    </el-aside>
                    <el-main style="width: 700px;">
                        <component :is="currentView"></component>
                    </el-main>
                </el-container>
            </div>
        </el-main>
        <el-footer style="background-color: black; height: 230px; margin-top: 20px;">

        </el-footer>
    </el-container>
</template>
  
<script setup>
import { ref, shallowRef } from "vue";
//import {userInfoService} from "@/api/user.js"
import useUserInfoStore from "@/stores/userinfo.js"
import testImg from "/images/student.jpg";
import headpart from "../components/headpart.vue";
import PersonContainer from "@/components/personContainer.vue";
import changepassword from "@/components/changepassword.vue"
import Subscribe from "@/components/subscribe.vue";
import history from "@/components/history.vue"
import uploadCourse from "@/components/uploadCourse.vue";
import examUpload from "@/components/examUpload.vue";
const currentView = shallowRef(PersonContainer)
const userInfoStore = useUserInfoStore()
const isIdentity = ()=>{
    if(userInfoStore.info.identity === "teacher"){
        return true
    }else if(userInfoStore.info.identity === "student"){
        return false
    }

}


const handleSelect = (key) => {

    if (key === 'PersonContainer') {
        currentView.value = PersonContainer;
    } else if (key === 'changepassword') {
        currentView.value = changepassword;
    }
    switch (key) {
    case 'PersonContainer':
        currentView.value = PersonContainer;
        break;
    case 'changepassword':
        currentView.value = changepassword;
        break;
    case 'Subscribe':
        currentView.value = Subscribe;
        break;
    case 'history':
        currentView.value = history;
        break;
    case 'uploadCourse':
        currentView.value = uploadCourse;
        break;
    case 'examUpload':
        currentView.value = examUpload;
        break;
    default:
        console.error(`未找到与 key "${currentKey.value}" 对应的组件`);
  }

}


</script>
  
<style scoped>
div {
    display: block;

}

.primary {
    margin-left: 200px;
    justify-content: flex-start;
    height: 48.75rem;
    position: static;
    top: 50%;
    left: 0;
    float: left;
    align-self: center;
}

.aside-div {
    height: 39.38rem;
}

span {
    padding-left: 16px;
}

.icon {
    margin-top: 3.44rem;
    margin-left: 3.13rem;
    width: 5.5rem;
    height: 5.5rem;
}

.picture {
    display: inline-block;
    width: 5.5rem;
    height: 5.38rem;
    border-radius: 0;
}

.menuset-div {
    margin-top: 1.25rem;
    margin-left: 1.5rem;
    width: 8.69rem;
    height: 280px;
}
</style> 