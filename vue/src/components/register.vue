<script setup>
import { ref, reactive} from 'vue'

const Visible = reactive({
    registerVisible: false
})
const registerFrom = reactive({
    username: '',
    password: '',
    Repassword: ''
})
const rePassword = (rule, value, callback) => {
    console.log(value);
    if (value === '') {
        callback(new Error('输入密码为空'))
    } else if (value !== registerFrom.password) {
        callback(new Error('两次密码不一致'))
    } else {
        callback()
    }
}
const registerRules = {
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    Repassword: [
        { validator: rePassword, trigger: 'blur' }
    ]

}
// const registerdDate = function () {
//     $emit('sendData',registerVisible.value)
//     console.log(registerFrom.username, registerFrom.password, registerFrom.Repassword);
//     registerFrom.username = ''
//     registerFrom.password = ''
// }
const sendDataTo = (data)=>{
    emit('getData',data)
}

const close = function () {
    Visible.registerVisible=false
    sendDataTo(Visible)
}
</script>
<template>   
    <el-form :mode="registerFrom" label-position="top" :rules="registerRules">
        <el-form-item label="用户名" prop="username">
            <el-input v-model="registerFrom.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
            <el-input v-model="registerFrom.password" placeholder="请输入密码" type="password" />
        </el-form-item>
        <el-form-item label="再次输入密码" prop="Repassword">
            <el-input v-model="registerFrom.Repassword" placeholder="请再次输入密码" type="password" />
        </el-form-item>
    </el-form>

    <div class="dialog-footer">
        <div class="button-container">
            <el-button @click="close">取消</el-button>
            <el-button type="primary" @click="registerdDate">
                注册
            </el-button>
        </div>
    </div>
</template>
<style scoped>
.dialog-footer {
    display: flex;
    justify-content: center;
    align-items: center;
}

.button-container {
    display: flex;
    gap: 30px;
}
</style>