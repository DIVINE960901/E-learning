<script setup>
import { reactive,ref } from "vue"
import {useTokenStore} from '@/stores/counter.js'
import useUserInfoStore from "@/stores/userinfo.js"
import { adminLoginService } from "@/api/admin.js"
import {userInfoService} from "@/api/user.js"
import { useRouter } from 'vue-router';
const router = useRouter();
const tokenStore = useTokenStore()
const userInfoStore = useUserInfoStore()
const loginData = ref([
  {
    username: "",
    password: ""
  }
])
const Rules = reactive({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],

})
const getUserInfo = async () => {
    let result = await userInfoService()
    userInfoStore.setInfo(result.data )
    router.push('/admin')
    
}
const loginAdmin = async() => {
  let result = await adminLoginService(loginData.value)
  ElMessage.success(result.message ? result.message : '登录成功')
  tokenStore.setToken(result.data)
  getUserInfo()
}
const login = (formEl) => {
    if (!formEl) return;

    formEl.validate((valid) => {
        if (valid) {
          loginAdmin()
            // console.log('登录成功');
        } else {
            // console.log('登录失败');
            ElMessage.error('登录失败');
            return false;
        }
    });


}

const reSet = () => {
  loginData.value = {
    username: "",
    password: ""
  }
}


</script>
<template>
  <div class="login_container">
    <div class="login_box">
      <!-- 头像区域 -->
      <div class="avatar_box">
        <img src="../assets/logo.svg" alt="" />
      </div>
      <!-- 登录表单区域 -->
      <el-form label-width="0px" class="login_form" label-position="top" :hide-required-asterisk="true" :rules="Rules" :model="loginData" ref="loginForm">
        <!-- 用户名 -->
        <el-form-item label="管理员账号" prop="username">
          <el-input v-model="loginData.username" placeholder="请输入账号"/>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item label="管理员密码" prop="password">
          <el-input v-model="loginData.password" placeholder="请输入密码" type="password" show-password/>
        </el-form-item>
        <!-- 按钮区域 -->
        <el-row justify="end">
          <el-form-item class="login_btn">
            <el-button type="primary" @click="()=>{login($refs.loginForm)}">登录</el-button>
            <el-button type="info" @click="reSet()">重置</el-button>
          </el-form-item>
        </el-row>
      </el-form>
    </div>
  </div>
</template>
   


   
<style scoped>
.login_container {
  background-color: #2b4b6b;
  height: 100vh;
}

.login_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);

    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
}

.login_form {
  position: absolute;
  bottom: 0;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

.login_btn {
  display: flex;
  justify-content: flex-end;
}
</style>