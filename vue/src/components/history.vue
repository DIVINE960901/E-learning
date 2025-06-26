<script setup>
import { ref, computed } from 'vue';
import { gethistoryService,deleteHistoryService } from '@/api/personPage.js';
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
const getHistory = async()=>{
    await gethistoryService(userInfoStore.info.id).then(res=>{
        //console.log(res)
        data.value = res.data
    })
}
getHistory()

function truncateTitle(title, maxLength) {
    if (title.length > maxLength) {
        return title.slice(0, maxLength) + '...';
    }
    return title;
}

const data = ref([
    // { id: "1", title: '前端入门 H5+CSS3+实战', src: '/images/5.jpg' },
    // { id: "2", title: '新版Java入门到起飞（全）', src: '/images/1.PNG' },
    // { id: "3", title: 'JAVA集合', src: '/images/2.jpg' },
    // { id: "4", title: 'JAVA面向对象', src: '/images/3.jpg' },
    // { id: "5", title: 'Java入门到起飞', src: '/images/4.jpg' },
    // { id: "6", title: '14天搞定JavaWeb 简洁高效 一套精通', src: '/images/6.jpg' },
    // { id: "7", title: 'JAVA基础语法', src: '/images/7.jpg' },
    // { id: "8", title: '20天Java基础入门 项目+真题', src: '/images/8.jpg' },
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

const goToVideo = (id)=>{
//    console.log(id);
    router.push({ name: 'course', params: { videoId: id} });
}

const deleteHis = async(a,b)=>{
    //console.log(a,b)
    await deleteHistoryService(a,b).then(res=>{
        //console.log(res)
        //console.log(a,b)
        getHistory()
        
    })
}



</script>
<template>
    <div class="course-list-container">
        <el-container>
            <el-main style="padding: 20px; height: 700px;">
                <el-space direction="vertical" class="card-space" :size="0">
                    <el-card v-for="List in pagedData" :key="List.videoId" style="" :body-style="cardCSS()">
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
                                            <el-button type="primary" size="small" @click="goToVideo(List.videoId)">查看课程</el-button>
                                            <el-button type="primary" size="small" @click="deleteHis(List.userId,List.videoId)">删除记录</el-button>
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