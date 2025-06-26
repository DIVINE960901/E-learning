<script setup>
import { ref } from 'vue'
import { findStateService,updateStateService } from '@/api/admin.js';
import player from '@/components/player.vue'
const centerCellStyle = () => {
    return { textAlign: 'center' }

}
const dialogVisible = ref(false)
const url = ref()
const getAllCourseList = async()=>{
  let result = await findStateService()
  tableData.value = result.data
  console.log(tableData.value);

}
getAllCourseList()
const handleClick = (index) => {
  //console.log(tableData.value[index].contentUrl)
  url.value = tableData.value[index].id
  // console.log(url.value)
  dialogVisible.value = true
}

const passClick=async(index)=>{
  const updataForm = {
    id:tableData.value[index].id,
    state:'已通过'
  }
  // console.log(tableData.value[index].id)
  let result = await updateStateService(updataForm)
  ElMessage.success(result.message?result.message:'修改成功')
  getAllCourseList()
}

const unPassClick=async(index)=>{
  const updataForm = {
    id:tableData.value[index].id,
    state:'未通过'
  }
  // console.log(tableData.value[index].id)
  let result = await updateStateService(updataForm)
  ElMessage.success(result.message?result.message:'修改成功')
  getAllCourseList()
}


const tableData = ref([])
</script>
<template>
  <el-table :data="tableData" style="width: 100%" table-layout="auto" :cell-style="centerCellStyle" :header-cell-style="centerCellStyle">
    <el-table-column fixed prop="createTime" label="创建日期" width="150" />
    <el-table-column prop="title" label="标题" width="120" />
    <el-table-column prop="state" label="审核状态" width="120" />
    <el-table-column prop="nickname" label="创建者" width="120" />
    <el-table-column label="封面图" width="600">
      <template #default="scope">
        <img :src="scope.row.coverImg" style="max-width: 100px; height: auto;" />
      </template>
    </el-table-column>
    <el-table-column fixed="right" label="Operations" width="180">
      <template #default="operate">
        <el-button link type="primary" size="small" @click="handleClick(operate.$index)">视频详情</el-button>
        <el-button link type="primary" size="small" @click="passClick(operate.$index)">通过</el-button>
        <el-button link type="danger" size="small" @click="unPassClick(operate.$index)">打回</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-dialog v-model="dialogVisible" destroy-on-close width="1000">
    <player :url="url"/>
  </el-dialog>
</template>
<style scoped></style>