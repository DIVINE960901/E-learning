<script setup>
import { ref } from 'vue'
import {updataRecommendService} from '@/api/admin.js'
import { getAllCourseService } from '../api/courseView.js'
import { ElMessage } from 'element-plus';
let postData  = []
const courseList = async () => {
  let result = await getAllCourseService()
  tableData.value = result.data
}
courseList()
const multipleTableRef = ref();
const multipleSelection = ref([]);
const tableData =ref([])

// function test(){
//     console.log(multipleSelection.value)
//     for(let i=0;i<multipleSelection.value.length;i++){
//         console.log(multipleSelection.value[i].id)

//     }
// }

function handleSelectionChange(val) {
  multipleSelection.value = val;
}

const test = async () => {
  if (multipleSelection.value.length < 9 && multipleSelection.value.length > 0) {
    try {
      for (let i = 0; i < multipleSelection.value.length; i++) {
        postData.push({
          id: multipleSelection.value[i].id,
          title: multipleSelection.value[i].title,
          coverImg: multipleSelection.value[i].coverImg,
          nickname: multipleSelection.value[i].nickname
        })
      }
      await  updataRecommendService(postData)
      ElMessage.success("修改成功")
      
      multipleSelection.value = []
    } catch (error) {
      ElMessage.error("修改失败")
    }
  } else {
    ElMessage.error("请选择8个以下的课程")
  }
  
postData = []
}

</script>
<template>
<el-table ref="multipleTableRef" :data="tableData" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column label="id" width="120">
            <template #default="scope">{{ scope.row.id }}</template>
        </el-table-column>
        <el-table-column property="createTime" label="创建时间" width="120" />
        <el-table-column property="nickname" label="创建者" show-overflow-tooltip />
        <el-table-column property="title" label="标题" show-overflow-tooltip />
        <el-table-column label="封面图" width="600">
            <template #default="scope">
                <img :src="scope.row.coverImg" style="max-width: 100px; height: auto;" />
            </template>
        </el-table-column>
    </el-table>
    <div style="margin-left: 500px">
        <el-button @click="test()" size="large" type="primary">确定</el-button>
    </div>
</template>
<style scoped>

</style>