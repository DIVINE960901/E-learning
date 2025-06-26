<script setup>
import { ref } from 'vue'
import { Fold, Expand, Message } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus';
//import a11Img from '/images/student.jpg' 
import useUserInfoStore from "@/stores/userinfo.js"
import {useTokenStore} from "@/stores/counter"
import router from '@/router';
const userInfoStore = useUserInfoStore()
const tokenStore = useTokenStore()
// console.log(userInfoStore.info.userPic);
// console.log(userInfoStore.info.nickname);
const isCollapse = ref(true)
// 获取父组件传递过来的数据 
const showIcon = defineProps({
    isCollapse: Boolean
})

// 获取父组件自定义的事件
const emit = defineEmits(['changeAside'])

// 自定义按钮点击事件，侧边栏的收缩按钮
const collapseAside = () => {
    emit('changeAside')
}

// 登出按钮
const LogOut = ()=>{
    userInfoStore.removeInfo()
    tokenStore.removeToken()
    console.log(userInfoStore.info);
    console.log(tokenStore.token);
    router.push('/login')
}

</script>


<template>
    <div>
        <el-header style="display: flex;font-size: 16px;">
            <div style="display: inline-flex;flex: 1; align-items: center;justify-content: left;">
                <el-icon v-show="!showIcon.isCollapse" @click="collapseAside" style="color: #303133;">
                    <Fold />
                </el-icon>
                <el-icon v-show="showIcon.isCollapse" @click="collapseAside" style="color: #303133;">
                    <Expand />
                </el-icon>
            </div>
            <span style="margin-right: 30px; margin-top: 20px;">管理员：{{userInfoStore.info.nickname}}</span>
            <div class="toolbar">
                <el-dropdown trigger="click">
                    
                    <div class="block" style="margin-right: 10px;">
                        <el-avatar :size="40" :src="userInfoStore.info.userPic" />
                    </div>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item>
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
        </el-header>
    </div>
</template>



<style scoped>
.el-header {
    width: 100%;
    background-color: #fff;
    color: var(--el-text-color-primary);
    box-shadow: var(--el-box-shadow);
}

.toolbar {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    right: 20px;
    text-align: right;
}

.el-dropdown-menu__item {
    width: 120px;
}

.icon-color {
    color: white;
}
</style>
