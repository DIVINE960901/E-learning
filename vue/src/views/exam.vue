<script setup>
//import { RouterLink, RouterView } from 'vue-router'
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userinfo.js'
import { Select, CloseBold } from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router';
import{ findTopicService,addDataSerivce } from '@/api/exam.js'
const route = useRoute();
const router = useRouter();
//后端传来题目与答案
const data = ref([])
const dataList = async()=>{
    //console.log(route.params.examId);
    let result = await findTopicService(route.params.examId);
    data.value = result.data;
}
dataList()
data.value.forEach(item => {
  item.showAnswer = false;
});
const radioAnswers = ref([]);
for (let i = 0; i < data.value.length; i++) {
    radioAnswers.value.push('');
}
const isSubmitted = ref(false);
const submit = () => {
    let hasIncompleteAnswers = false;
    for (let i = 0; i < data.value.length; i++) {
        if (!radioAnswers.value[i]) {
            hasIncompleteAnswers = true;
            break;
        }
        const isCorrectAnswer = radioAnswers.value[i] === data.value[i].answer;
        data.value[i].isCorrect = isCorrectAnswer;
        console.log(data.value[i].isCorrect);

    }
    if (hasIncompleteAnswers) {
        alert("请填写完整");
    } else {
        isSubmitted.value = true;
        console.log(radioAnswers.value);
        data.value.forEach(item => item.showAnswer = true);

        let num = 0;
        data.value.forEach(count => {
            if (count.isCorrect) {
                num += 1;
            }
        });

        let addDataUserId = useUserInfoStore().info.id;
        let addDataExamId = route.params.examId;
        let addDataAccuracy = (num/data.value.length).toFixed(2);
        addDataSerivce(addDataUserId,addDataExamId,addDataAccuracy)

        // console.log((num/data.value.length).toFixed(2))
        // console.log(route.params.examId);
        // console.log(useUserInfoStore().info.id);

        
    }
};
const backToList = ()=>{
    router.push('/testlist');
}


</script>

<template>
    <div style="max-width: 1500px; margin: auto;">
        <el-space direction="vertical" class="card-list">
            <el-card v-for="(item, index) in data" :key="index" style="width: 850px;">
                <template #header>
                    <div class="card-header">
                        <span class="question-number">{{ index + 1 }}、</span>
                        <span>{{ item.topicTitle }}</span>
                        <div class="icon-container" v-if="isSubmitted">
                            <el-icon :color="item.isCorrect ? 'green' : 'red'">
                                <component :is="item.isCorrect ? Select : CloseBold" />
                            </el-icon>
                        </div>
                    </div>
                </template>
                <el-radio-group v-model="radioAnswers[index]" class="answer-options">
                    <el-space direction="vertical" wrap>
                        <el-radio label="A" class="radio-item"><span>A.</span>{{ item.optionA }}</el-radio>
                        <el-radio label="B" class="radio-item"><span>B.</span>{{ item.optionB }}</el-radio>
                        <el-radio label="C" class="radio-item"><span>C.</span>{{ item.optionC }}</el-radio>
                        <el-radio label="D" class="radio-item"><span>D.</span>{{ item.optionD }}</el-radio>
                    </el-space>
                </el-radio-group>
                <template #footer>
                    <div class="answer-container" v-if="item.showAnswer || isSubmitted"><span>答案：{{ item.answer }}</span></div>
                </template>
            </el-card>
        </el-space>
        <div class="submit-btn-container">
            <el-button type="primary" size="large" @click="submit">提交</el-button>
            <el-button style="margin-left: 50px;" size="large" @click="backToList">返回</el-button>
        </div>

    </div>
</template>

<style scoped>
.quiz-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 30px;
}

.card-list {
    width: 100%;
    box-sizing: border-box;
}

.card-header {
    display: flex;
    align-items: center;
    justify-content: left;
    font-weight: bold;
    padding: 16px 20px;
    /* border-bottom: 1px solid #ebeef5; */
}

.icon-container {
    display: flex;
    align-items: center;
    margin-left: 15px;
    /* 将图标移至右侧并靠左对齐 */
}

.question-number {
    color: #909399;
    font-size: 16px;
}
.answer-container{
    display: flex;
}
.answer-options {
    display: flex;
    flex-direction: flex-start;
    margin-left: 20px;
    padding: 20px;
}

.submit-btn-container {
    text-align: center;
    margin-top: 30px;
}
</style>