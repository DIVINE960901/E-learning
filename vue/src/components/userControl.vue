<script setup>
import { ref,reactive } from 'vue'
import { getAllUserService,updateUserService,deleUserService } from '@/api/admin.js'
import { userRegisterService } from '../api/user.js'
import { ElMessage } from 'element-plus';
const centerCellStyle = () => {
    return { textAlign: 'center' }

}

const userList = async () => {
    let result = await getAllUserService()
    tableData.value = result.data
}
userList()
const addUserVisible = ref(false)
const changeUserVisible = ref(false)
const tableData = ref([])
const FromData = reactive({
    username: '',
    password: '',
    Repassword: '',
})
const identityGroup = ref([
    {
        label: '管理员',
        value: 'admin'
    },
    {
        label: '学生',
        value: 'student'
    },
    {
        label: '教师',
        value: 'teacher'
    }
])
const postData = ref([
    {
        id: '',
        username: '',
        nickname: '',
        identity: ''
    }
])
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
const handleClick = (index) => {
    changeUserVisible.value = true
    console.log(index)
    postData.value = tableData.value[index]
}

const deleteClick = async(index) => {
    //console.log(tableData.value[index].id)
    let result = await deleUserService(tableData.value[index].id)
    ElMessage.success(result.message?result.message:'删除成功')
    userList()
}
const setNewData = async() => {
    let result = await updateUserService(postData.value)
    // console.log(postData.value)
    // console.log(result);
    ElMessage.success(result.message?result.message:'修改成功')
    changeUserVisible.value = false
    userList()
}
const registerdDate = async()=>{
    let result = await userRegisterService(FromData)
    ElMessage.success(result.message ? result.message : '注册成功')
    //alert(result.msg ? result.msg : '注册成功')
    addUserVisible.value = false
    userList()
}
const clearFrom = ()=>{
    FromData.username=''
    FromData.password=''
    FromData.Repassword=''
}

</script>
<template>
    <div>
        <el-table :data="tableData" style="width: 100%" table-layout="auto" :cell-style="centerCellStyle"
            :header-cell-style="centerCellStyle">
            <el-table-column fixed prop="id" label="用户ID" width="150" />
            <el-table-column prop="username" label="账号" width="120" />
            <el-table-column prop="nickname" label="昵称" width="120" />
            <el-table-column prop="identity" label="身份" width="120" />
            <el-table-column fixed="right" label="Operations" width="180">
                <template #default="operate">
                    <el-button link type="primary" size="small" @click="handleClick(operate.$index)">修改信息</el-button>
                    <el-button link type="danger" size="small" @click="deleteClick(operate.$index)">删除用户</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-button class="mt-4" style="width: 100%" @click="addUserVisible = true">新增用户</el-button>
    </div>
    <el-dialog title="修改用户信息" v-model="changeUserVisible" width="30%">
        <el-form label-position="top" :model="postData">
            <el-form-item label="账号">
                <el-input v-model="postData.username" type="text" disabled/>
            </el-form-item>
            <el-form-item label="昵称">
                <el-input v-model="postData.nickname" />
            </el-form-item>
            <el-form-item label="身份">
                <!-- <el-input-number v-model="tableData.singleNumber"/> -->
                <el-select v-model="postData.identity" class="m-2" placeholder="Select" size="large" style="width: 240px">
                    <el-option v-for="item in identityGroup" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
            </el-form-item>
            <div style="text-align: center">
                <el-button type="primary" size="small" @click.prevent="setNewData()">
                    确认
                </el-button>
            </div>
        </el-form>
    </el-dialog>
    <el-dialog title="新增用户信息" v-model="addUserVisible" width="30%">
        <el-form :model="FromData" :rules="Rules" label-position="top" :hide-required-asterisk="true">
            <el-form-item label="账号" prop="username">
                <el-input v-model="FromData.username" placeholder="请输入账号" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="FromData.password" placeholder="请输入密码" type="password" show-password/>
            </el-form-item>
            <el-form-item label="再次输入密码" prop="Repassword">
                <el-input v-model="FromData.Repassword" placeholder="请再次输入密码" type="password" show-password/>
            </el-form-item>
        </el-form>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="addUserVisible = false;clearFrom()">取消</el-button>
                <el-button type="primary" @click="()=>{registerdDate();clearFrom()}">
                    注册
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>
<style scoped></style>