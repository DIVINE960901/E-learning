<template>
    <div class="Pcontainer">
        <el-form label-position="top" :model="form" style="width: fit-content;">
            <el-form-item label="用户名" class="labelN">
                <el-input v-model="form.username" placeholder="请输入新的用户名" disabled/>
            </el-form-item>
            <el-form-item label="昵称" class="labelN">
                <el-input v-model="form.nickname" placeholder="请输入新的昵称"/>
            </el-form-item>
            <el-form-item style="margin-left: 60px;">
                <el-space wrap :size="50">
                    <el-button type="primary" @click="onSubmit">确定</el-button>
                    <el-button>重置</el-button>
                </el-space>
            </el-form-item>
        </el-form>
    </div>
</template>
<script setup>
import { ref,reactive } from 'vue'
import useUserInfoStore from "@/stores/userinfo.js"
import {userUpdateService} from '@/api/personPage.js'
import { ElMessage } from 'element-plus'
const userInfoStore = useUserInfoStore()
const form = ref({...userInfoStore.info})
const onSubmit = async() => {
    let result = await userUpdateService(form.value)
    ElMessage.success(result.message?result.message:'修改成功')
    userInfoStore.setInfo(form.value)
}
// export default {
//     setup() {
        
//         return {
//             form,
//             onSubmit
//         }
//     }
// }
</script>
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