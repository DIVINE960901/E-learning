<script setup>
import { ref,onMounted } from 'vue'
import { updataCarouslService,getCarouselService } from '@/api/admin.js'
import { getAllCourseService } from '../api/courseView.js'
import { ElMessage } from 'element-plus';
const getData = ref([])
let postData = []
const centerCellStyle = () => {
  return { textAlign: 'center' }

}

const carouselList =  async() => {
  let result = await getCarouselService()
  getData.value = result.data
  console.log(getData.value);
}
const courseList = async () => {
  let result = await getAllCourseService()
  tableData.value = result.data
}
carouselList()
courseList()
console.log(getData.value);
const multipleTableRef = ref();
const multipleSelection = ref([]);


const test = async () => {
  //console.log(multipleSelection.value)

  if (multipleSelection.value.length < 5) {
    ElMessage.error("请选择5个以上的课程")

  } else if (5 <= multipleSelection.value.length && multipleSelection.value.length <= 8) {
    try {
      //console.log(multipleSelection.value);
      for (let i = 0; i < multipleSelection.value.length; i++) {
        postData.push({
          id: multipleSelection.value[i].id,
          title: multipleSelection.value[i].title,
          coverImg: multipleSelection.value[i].coverImg,
          nickname: multipleSelection.value[i].nickname
        })
      }
      //console.log(postData)
      await  updataCarouslService(postData)
      ElMessage.success("修改成功")
      
      multipleSelection.value = []
    } catch (error) {
      ElMessage.error("修改失败")
    }
    // console.log(multipleSelection.value[i].id)
  } else {
    ElMessage.error("请选择8个以下的课程")

  }
//console.log(postData)
postData = []
//console.log(postData)
carouselList()  


}

function handleSelectionChange(val) {
  multipleSelection.value = val;
}
const tableData = ref([])
</script>
<template>
  <div>
    <el-carousel v-if="getData.length > 0" :interval="4000" type="card" height="300px">
      <el-carousel-item v-for="(item, index) in getData" :key="index">
        <!-- <h3 text="2xl" justify="center">{{ item }}</h3> -->
        <!-- <img :src="item.coverImg" /> -->
        <el-image style="width: 100%; height: 100%" :src="item.coverImg" fit="fill" />
      </el-carousel-item>
    </el-carousel>
  </div>
  <el-table ref="multipleTableRef" :cell-style="centerCellStyle" :header-cell-style="centerCellStyle" :data="tableData"
    max-height="600" style="width: 100%" @selection-change="handleSelectionChange">
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
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}
</style>