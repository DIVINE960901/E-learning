<script setup>
import { ref } from 'vue'
import { findAllExamService } from '@/api/exam.js'
import headpart from '../components/headpart.vue'
import { useRouter } from 'vue-router';
const router = useRouter();

const pageNum = ref(1)
const pageSize = ref(10)

const data = ref([])
const totalPages = ref();
const examList = async () => {
    let params = {
        pageNum: pageNum.value,
        pageSize: pageSize.value
    }
    let result = await findAllExamService(params)
    data.value = result.data.items;
    totalPages.value = result.data.total
}
examList()

const scrollToTop = () => {
  window.scrollTo({ top: 0, behavior: 'smooth' });
};

const onPageChange = (currentPage) => {
    pageNum.value = currentPage;
    examList()
    scrollToTop()
};
const goToExam = (examId) => {
    //console.log(totalPages.value);
    router.push({ name: 'exam', params: { examId} });
}

</script>
<template>
    <el-container style="max-width: 1500px; margin: auto;">
        <el-header>
            <headpart />
        </el-header>
        <el-container>
            <el-main style="margin-top: 20px; height: 2350px;">
                <div class="outer-container">
                    <el-space direction="vertical">
                        <el-card shadow="never" v-for="i in data " :key="i.id" style="height: 220px;width: 760px;">
                            <template #header>
                                <div class="list-header">
                                    <span>{{ i.title }}</span>
                                    <el-button type="success" @click="goToExam(i.id)">开始测试</el-button>
                                </div>
                            </template>
                            <el-container>
                                <el-main>
                                    <el-descriptions :column="2" size="small">
                                        <el-descriptions-item label="题数">{{ i.singleNumber }}</el-descriptions-item>
                                        <el-descriptions-item label="创建人">{{ i.nickname }}</el-descriptions-item>
                                        <el-descriptions-item label="描述">
                                            {{ i.describe }}
                                        </el-descriptions-item>
                                    </el-descriptions>
                                </el-main>
                            </el-container>
                        </el-card>
                    </el-space>
                </div>
            </el-main>
            <el-footer style="
                padding: 0px;
                display: flex;
                justify-content: center; /* 水平居中 */
                align-items: center; /* 垂直居中 */
                padding: 0px;">
                <el-pagination v-if="!!totalPages" background layout="prev, pager, next" :total="totalPages" :hide-on-single-page="true"
                    :page-size="pageSize" @current-change="onPageChange" />
            </el-footer>
        </el-container>
        <el-footer style="background-color: black; height: 230px;">

        </el-footer>
    </el-container>
</template>
<style scoped>
.outer-container {
    width: 800px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 0 auto;
}

.list-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
}
</style>