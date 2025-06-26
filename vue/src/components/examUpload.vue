<script  setup>
import { ref, reactive, watch } from 'vue'
import { addExamService, examDetailService, addTopicService,getTopicService ,deleteExamService} from '@/api/personPage.js'
import useUserInfoStore from '@/stores/userinfo.js'
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';
const router = useRouter();

const uploadVisible = ref(false)
const questionSetting = ref(false)

const setQN = ref(1)
const getData = ref([])
const postData = reactive([])
//const uploadData = reactive([])
//const settingData = ref([])


const centerCellStyle = () => {
    return { textAlign: 'center' }

}
const topicResult = async (index) => {
    // console.log(getData.value[index].singleNumber);
    let result = await getTopicService(getData.value[index].id)
    if(result.data.length ===0){
        // console.log("true");
        return true
    }else{
        // console.log("false");
        return false
    }
    //console.log(result.data);
}
const examSetList = async () => {
    let result = await examDetailService()
    //getData.value = result.data
     console.log(result.data);
    
    getData.value = result.data.map((item, index) => ({
    ...item,
    isInputDisabled: false // 初始化为可显示
  }));
  //console.log(getData.value[2].id);

  // 遍历每一项并判断是否应该隐藏按钮
  for (let i = 0; i < getData.value.length; i++) {
    const shouldHideButton = !(await topicResult(i));
    getData.value[i].isInputDisabled = shouldHideButton;
  }
}
examSetList()



const Rules = reactive({
    title: [
        { required: true, message: '请填写题目', trigger: 'blur' },
    ],
    singleNumber: [
        { required: true, message: '填写题数', trigger: 'blur' },
    ],
    topicTitle: [
        { required: true, message: '填写题目', trigger: 'blur' },
    ],
    optionA: [
        { required: true, message: '填写选项A内容', trigger: 'blur' },
    ],
    optionB: [
        { required: true, message: '填写选项B内容', trigger: 'blur' },
    ],
    optionC: [
        { required: true, message: '填写选项C内容', trigger: 'blur' },
    ],
    optionD: [
        { required: true, message: '填写选项D内容', trigger: 'blur' },
    ],
    answer: [
        { required: true, message: '选择正确的答案', trigger: 'blur' },
    ],

})
const settingFormData = reactive([
    {
        createTime: '',
        title: '',
        singleNumber: '',
        nickname: '',
        describe: ''
    }
])
const resetSetttingForm = () => {
    settingFormData.title = '';
    settingFormData.singleNumber = 1;
    settingFormData.describe = '';
}

const addExam = async (addExamData) => {
    let result = await addExamService(addExamData)
    console.log(result);
    ElMessage.success(result.message ? result.message : '上传成功')
}

const addRow = (formEl) => {
    if (!formEl) return;

    formEl.validate((valid) => {
        if (valid) {
            const newData = {
                title: settingFormData.title,
                singleNumber: settingFormData.singleNumber,
                nickname: useUserInfoStore().info.nickname,
                describe: settingFormData.describe
            }
            //            settingData.value.push(newData)
            addExam(newData)
            resetSetttingForm()
            //            console.log(settingData);
            examSetList()
            questionSetting.value = false;;
        } else {
            return false;
        }
    });


}

const getSettingNumber = (index) => {
    setQN.value = getData.value[index].singleNumber;
    // console.log(getData[index].number);
    postData.length = 0;
    const newArray = Array(setQN.value).fill(0).map((_, i) => ({
        topicTitle: '',
        optionA: '',
        optionB: '',
        optionC: '',
        optionD: '',
        answer: '',
        examId: getData.value[index].id,

    }));
    postData.push(...newArray);
    //console.log(postData);
}

const deleteRow = async(index) => {
    // getData.value[index].id
    let result = await deleteExamService(getData.value[index].id)
    if(result.data == true){
        examSetList()
        //getData.splice(index, 1)//返回结果后再执行
        ElMessage.success('删除成功')
    }else{
        ElMessage.error('删除失败')
    }
    

}

const resetData = () => {
    for (let i = 0; i < postData.length; i++) {
        postData[i] = {
            topicTitle: '',
            optionA: '',
            optionB: '',
            optionC: '',
            optionD: '',
            answer: '',
        };
    }
}

// const addTopic = async(addTopicData) => {
//     let result = await addTopicService(addTopicData)
//     return result
// }

const show = async () => {

    // uploadData.push(postData[i]);
try {
    for (let i = 0; i < postData.length; i++) {

        await addTopicService(postData[i])
    }
    ElMessage.success('上传成功')
    examSetList()
} catch (error) {
    ElMessage.error('上传失败')
}   
    //    addTopic(uploadData)
    resetData()
    //    console.log(uploadData);
}

const getExamId = (index)=>{
    console.log(getData.value[index].id);
    router.push({ name: 'showExamData', params: { id: getData.value[index].id } });
}


</script>
<template>
    <div>
        <el-table :data="getData" :cell-style="centerCellStyle" :header-row-style="centerCellStyle" style="width: 100%"
            max-height="600" table-layout="auto">
            <el-table-column fixed prop="createTime" label="创建时间" width="150" />
            <el-table-column prop="title" label="标题" width="120" />
            <el-table-column prop="singleNumber" label="题目数量" width="120" />
            <el-table-column prop="nickname" label="创建者" width="120" />
            <el-table-column fixed="right" label="操作" width="160">
                <template #default="scope">
                    <el-button v-if="!scope.row.isShownDetails && !scope.row.isInputDisabled" type="primary" size="small"
                        @click.prevent="uploadVisible = true; getSettingNumber(scope.$index); scope.row.isShownDetails = true;">
                        输入题目
                    </el-button>
                    <el-button  type="success" size="small" @click.prevent="getExamId(scope.$index);">
                        查看数据
                    </el-button>
                    <el-button type="danger" size="small" @click.prevent="deleteRow(scope.$index)">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-button class="mt-4" style="width: 100%" @click="questionSetting = true">添加</el-button>
    </div>
    <el-dialog v-model="questionSetting" title="上传试题" width="700">
        <el-form label-position="top" :model="settingFormData" :rules="Rules" ref="settingForm">
            <el-form-item label="标题" prop="title">
                <el-input v-model="settingFormData.title" type="text" maxlength="30" placeholder="请输入标题" show-word-limit />
            </el-form-item>
            <el-form-item label="题数" prop="singleNumber">
                <el-input-number v-model="settingFormData.singleNumber" :min="1" :max="100" />
            </el-form-item>
            <el-form-item label="试题描述">
                <el-input v-model="settingFormData.describe" type="textarea" :autosize="{ minRows: 4, maxRows: 5 }"
                    maxlength="200" placeholder="试题描述" show-word-limit />
            </el-form-item>
            <div style="text-align: center">
                <el-button size="small" @click.prevent="resetSetttingForm()">
                    重置
                </el-button>
                <el-button type="primary" size="small" @click.prevent="() => { addRow($refs.settingForm) }">
                    确认
                </el-button>
            </div>
        </el-form>
    </el-dialog>
    <el-dialog v-model="uploadVisible" title="上传试题" width="700" :close-on-click-modal="false">
        <div v-for=" ( i, index ) in setQN" :key="index">
            <el-form label-position="top" :model="postData[i - 1]" :rules="Rules">
                <el-form-item label="题目" prop="topicTitle">
                    <el-input v-model="postData[i - 1].topicTitle" type="textarea" :autosize="{ minRows: 4, maxRows: 5 }"
                        maxlength="200" placeholder="请输入标题" show-word-limit />
                </el-form-item>
                <el-form-item label="选项A" prop="optionA">
                    <el-input v-model="postData[i - 1].optionA" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }"
                        maxlength="200" placeholder="请输入选项A内容" show-word-limit />
                </el-form-item>
                <el-form-item label="选项B" prop="optionB">
                    <el-input v-model="postData[i - 1].optionB" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }"
                        maxlength="200" placeholder="请输入选项B内容" show-word-limit />
                </el-form-item>
                <el-form-item label="选项C" prop="optionC">
                    <el-input v-model="postData[i - 1].optionC" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }"
                        maxlength="200" placeholder="请输入选项C内容" show-word-limit />
                </el-form-item>
                <el-form-item label="选项D" prop="optionD">
                    <el-input v-model="postData[i - 1].optionD" type="textarea" :autosize="{ minRows: 2, maxRows: 4 }"
                        maxlength="200" placeholder="请输入选项D内容" show-word-limit />
                </el-form-item>
                <el-form-item label="答案" prop="answer">
                    <el-radio-group v-model="postData[i - 1].answer">
                        <el-radio label="A">A</el-radio>
                        <el-radio label="B">B</el-radio>
                        <el-radio label="C">C</el-radio>
                        <el-radio label="D">D</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
        </div>

        <div style="text-align: center">
            <el-button size="small" @click.prevent="resetData()">重置</el-button>
            <el-button type="primary" size="small" @click.prevent="show(); uploadVisible = false">确认</el-button>
        </div>
    </el-dialog>
</template>
<style></style>