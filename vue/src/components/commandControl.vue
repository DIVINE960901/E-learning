<script setup>
import { ref } from 'vue'
import { getAllCommentService,delCommentService } from '@/api/admin.js'
const centerCellStyle = () => {
    return { textAlign: 'center' }

}

const commentList = async() =>{
    let result = await getAllCommentService()
    tableData.value = result.data
}
commentList()

const tableData = ref([
  // {
  //   id:'1',
  //   createTime: '2016-05-03',
  //   nickname: 'Tom',
  //   title: 'California',
  //   content: 'Los Angeles',

  // },

])
const deleteRow = async(index) => {
    //console.log(tableData.value[index].id);
    let result = await delCommentService(tableData.value[index].id);
    if(result.code == 0){
        commentList()
        // tableData.value.splice(index, 1)
    }
}
</script>
<template>
<el-table :data="tableData" style="width: 100%" table-layout="auto" :cell-style="centerCellStyle" :header-cell-style="centerCellStyle">
    <el-table-column fixed prop="createTime" label="创建日期" width="150" />
    <el-table-column prop="id" label="评论id" width="120" />
    <el-table-column prop="title" label="标题" width="120" />
    <el-table-column prop="nickname" label="用户名" width="120" />
    <el-table-column prop="content" label="评论内容" width="600" />
    <el-table-column fixed="right" label="操作" width="180">
      <template #default="scope">
        <el-button link type="danger" size="small" @click="deleteRow(scope.$index);dialogVisible = true">
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>
<style scoped>

</style>