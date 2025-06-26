<template>
    <el-carousel v-if="getData.length > 0" :interval="4000" type="card" height="300px">
        <el-carousel-item v-for="(item,index) in getData" :key="index">
            <!-- <h3 text="2xl" justify="center">{{ item.src }}</h3> -->
            <el-image style="width: 100%; height: 100%" :src="item.coverImg" fit="fill" />
        </el-carousel-item>
    </el-carousel>
</template>
  
<style scoped>
.el-carousel__item h3 {
    color: #475669;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
    text-align: center;
}

/* .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
  } */
</style>
<script setup>
import { ref } from 'vue'
import { getCarouselService } from '@/api/admin.js'
const getData = ref([])
const carouselList =  async() => {
  let result = await getCarouselService()
  getData.value = result.data
  console.log(getData.value);
}
carouselList()

</script>