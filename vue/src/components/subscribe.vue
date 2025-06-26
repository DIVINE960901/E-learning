<script setup>
import { ref, computed } from 'vue';
import {getFdetailService,deleteSubService} from '@/api/personPage.js'
import useUserInfoStore from "@/stores/userinfo.js"
import { useRouter } from 'vue-router';
const router = useRouter();
const userInfoStore = useUserInfoStore()
const cardCSS = ()=>{
    return{
        width: 400+'px', 
        padding: 0,
    }
}
const getSubscribe = async()=>{
    await getFdetailService(userInfoStore.info.id).then(res=>{
        //console.log(res)
        data.value = res.data
        console.log(data.value);
    })
}
getSubscribe()
function truncateTitle(title, maxLength) {
    if (title.length > maxLength) {
        return title.slice(0, maxLength) + '...';
    }
    return title;
}

const data = ref([

])
const pageSize = ref(5);

// 计算总页数
const totalItems = computed(() => data.value.length);


// 当前页码
let currentPage = ref(1);

// 分页数据（当前页的数据）
const pagedData = computed(() => {
    const startIndex = (currentPage.value - 1) * pageSize.value;
    return data.value.slice(startIndex, startIndex + pageSize.value);
});

function handlePageChange(page) {
    currentPage.value = page;
}

const getVideoId = (id)=>{
//    console.log(id);
    router.push({ name: 'course', params: { videoId: id} });
}

const deleteSub = async(a,b)=>{
    //console.log(a,b)
    await deleteSubService(a,b).then(res=>{
        //console.log(res)
        //console.log(a,b)
        getSubscribe()
        
    })
}

</script>
<template>
    <div class="course-list-container">
        <el-container>
            <el-main style="padding: 20px; height: 700px;">
                <el-space direction="vertical" class="card-space" :size="0">
                    <el-card v-for="List in pagedData" :key="List.videoId" style="" :body-style="cardCSS()" >
                        <div class="common-layout">
                            <el-container>
                                <el-aside style="width: 150px;">
                                    <router-link :to="{name: 'course', params: { videoId: List.videoId}}" class="course-link">
                                        <img :src="List.coverImg" alt="" class="course-image">
                                    </router-link>
                                </el-aside>
                                <el-container>
                                    <el-main style="padding: 10px;">
                                        <div class="course-info">
                                            <router-link :to="{name: 'course', params: { videoId: List.videoId}}" class="course-title"
                                                 :title="List.title" style="text-decoration-line: none;color: black;font-size: 14px;">
                                                {{ truncateTitle(List.title, 20) }}
                                            </router-link>
                                        </div>
                                    </el-main>
                                    <el-footer>
                                        <div class="course-actions">
                                            <el-button type="primary" size="small" @click="getVideoId(List.videoId)">查看课程</el-button>
                                            <el-button type="primary" size="small" @click="deleteSub(List.userId,List.videoId)">取消收藏</el-button>
                                        </div>
                                    </el-footer>
                                </el-container>
                            </el-container>
                        </div>
                    </el-card>
                </el-space>
            </el-main>
            <el-footer style="
                padding: 0px;
                display: flex;
                justify-content: center; /* 水平居中 */
                align-items: center; /* 垂直居中 */
                padding: 0px;">
                <el-pagination background layout="prev, pager, next" :total="totalItems" :page-size="pageSize"
                    @current-change="handlePageChange" />
            </el-footer>
        </el-container>
    </div>
</template>
<style scoped>
.course-list-container {
    max-width: 1200px;
    margin: 0 auto;
}

.el-card {
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
    width: 400px;
    height: 100px;
}

.el-card__body {
    padding: 5px;
}

.card-space {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
}

.el-card {
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.course-image {
    width: 100%;
    object-fit: cover;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
}

.course-info {
    padding: 5px;
    text-align: center;
    border-bottom-left-radius: 4px;
    border-bottom-right-radius: 4px;
}

auto .course-title {
    font-size: 16px;
    text-decoration: none;
    display: inline-block;
    margin-bottom: 8px;
    text-decoration-line: none;
}

.course-title>.el-button {
    text-align: center;
    width: 100%;
    padding: 0;

}

.course-actions {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 0;
}

</style>