<script setup>
import { reactive } from 'vue'
import useUserInfoStore from "@/stores/userinfo.js"
import { updatePwdService } from "@/api/personPage.js"
import { ElMessage } from 'element-plus';
const userInfoStore = useUserInfoStore()
const form = reactive({
    old_pwd:'',
    new_pwd: '',
    re_pwd: ''
})

const onSubmit = async() => {
    let result = await updatePwdService(form)
    ElMessage.success(result.message? result.message : '修改成功')
    reset()
}
const reset = () => {
    form.old_pwd = ''
    form.new_pwd = ''
    form.re_pwd = ''
}
// export default {
//     setup() {
        
        
//         return {
//             form,
//             onSubmit,
//             reset
//         }
//     }
// }
</script>
<template>
<div class="Pcontainer">
    <el-form label-position="top" :model="form" style="width: fit-content;">
    <el-form-item label="旧密码" class="labelN">
        <el-input v-model="form.old_pwd" placeholder="请输入旧密码" show-password></el-input>
    </el-form-item>
    <el-form-item label="新密码" class="labelN">
        <el-input v-model="form.new_pwd" placeholder="请输入新密码" show-password></el-input>
    </el-form-item>
    <el-form-item label="确认密码" class="labelN">
        <el-input v-model="form.re_pwd" placeholder="请确认密码" show-password></el-input>
    </el-form-item>
    <el-form-item style="margin-left: 60px;">
        <el-space wrap :size="50">
      <el-button type="primary" @click="onSubmit">确定</el-button>
      <el-button @click="reset">重置</el-button>
    </el-space>
    </el-form-item>
    
</el-form>
</div>

</template>
<style scoped>
.labelN {
    width: 300px;
}
.Pcontainer{
    display: flex;
    justify-content: center;
    align-items: center;
    height: 35vh
}
</style>