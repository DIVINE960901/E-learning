<script setup>
import {ref} from 'vue'
import headpart from '../components/headpart.vue'
import courseView from '../components/courseView.vue'
import carousel from '../components/carousel.vue'
import footerPart from '@/components/footerPart.vue'
import {getRecommendService} from '@/api/admin.js'

const Data = ref([])
const RecommendList = async()=>{
    let reuslt = await getRecommendService()
    Data.value = reuslt.data
}
RecommendList()
</script>

<template>
    <el-container style="max-width: 1500px; margin: auto;">
        <el-header>
            <headpart />
        </el-header>
        <el-main style="margin-top: 20px;">
            <el-container>
                <el-header style="height: auto;">
                    <carousel />
                </el-header>
                <el-main>
                    <el-space wrap size="large">
                        <courseView v-if="Data.length>0" v-for="i in Data" :key="i.id" :id="i.videoId" :coverImg="i.coverImg" :title="i.title" :createUser="i.nickname" />
                    </el-space>

                    <!-- <test/>
                <cardtest/> -->
                </el-main>
            </el-container>
        </el-main>
        <el-footer style="background-color: black; height: 230px; margin-top: 20px;">
            <footerPart/>
        </el-footer>
    </el-container>
</template>

<style></style>
