<script  setup>
import { ref } from 'vue'
import { addCourseService,deleteCourseService } from '@/api/personPage.js'
import {getAllCourseService} from '@/api/admin.js'
import { ElMessage, genFileId } from 'element-plus'
import { Delete, Plus, Finished } from '@element-plus/icons-vue'
const uploadVisible = ref(false)
const centerCellStyle = () => {
    return { textAlign: 'center' }

}
const courseList = async()=>{
    let result = await getAllCourseService()
    getData.value = result.data 
    //console.log(result);
}
courseList()
const getData = ref([
    // {
    //     id:'AAA',
    //     createTime: '2024/2/21',
    //     title: 'jjjj',
    //     state: '审核中',
    //     nickname: 'aaaa',
    //     Collections: '7',
    //     viewers: '8'
    // },
])
const uploadImg = ref()
const postData = ref({
    id:'',
    title: '',
    coverImg: '',
    state: ''
})
//const dialogImageUrl = ref('')
// const dialogVisible = ref(false)
const disabled = ref(false)
const uploadRef = ref(null)

// 提交上传的方法
const submitUpload = () => {
    // 在组件已挂载后调用submit方法
    if (uploadRef.value) {
        uploadRef.value.submit()
    }


}
const coverUpload = () => {
    if (postData.value.coverImg === '') {
        console.log("upload");
        if (uploadImg.value) {
            uploadImg.value.submit()
        }
    } else {
        ElMessage({
            message: '图片已上传',
            type: 'warning'
        })
    }

}
const handleExceed = (files) => {
    uploadImg.value.clearFiles();
    const file = files[0];
    file.uid = genFileId();
    uploadImg.value.handleStart(file);
};
const imageUploadSuccess = (response, file, fileList) => {
    //console.log(response.data);
    //console.log(postData.value);
    postData.value.coverImg = response.data
    //file.url = postData.value.coverImg
    
}
const handleVodUploadSuccess = (response, file, fileList) => {
    console.log(response.data);
    postData.value.id = response.data
    postData.value.state = '待审核'
    console.log(postData.value);
}

const uploadCourseData = async() => {
    let result = await addCourseService(postData.value)
    
    ElMessage.success(result.message? result.message:'上传成功')
    
    uploadVisible.value = false

    courseList()

}

const deleteRow = async(index) => {
    console.log(getData.value[index].id);
    let result = await deleteCourseService(getData.value[index].id)
    ElMessage.success(result.message? result.message:'删除成功')
    courseList()
    //getData.value.splice(index, 1)//返回结果后再执行
}



</script>
<template>
    <div>
        <el-table :data="getData" :cell-style="centerCellStyle" :header-cell-style="centerCellStyle" table-layout="auto"
            style="width: 100%" max-height="600">
            <el-table-column fixed prop="createTime" label="创建时间" width="150" />
            <el-table-column prop="title" label="标题" width="120" />
            <el-table-column prop="state" label="状态" width="120" />
            <el-table-column prop="nickname" label="创建者" width="120" />
            <el-table-column prop="numberOfCollections" label="收藏人数" width="120" />
            <el-table-column prop="numberOfViewers" label="观看人数" width="120" />
            <el-table-column fixed="right" label="操作" width="120">
                <template #default="scope">
                    <el-button type="danger" size="small" @click.prevent="deleteRow(scope.$index)">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-button class="mt-4" style="width: 100%" @click="uploadVisible = true">添加</el-button>
    </div>
    <el-dialog v-model="uploadVisible" title="上传课程" width="700" :close-on-click-modal="false">
        <el-form label-position="top" :model="postData">
            <el-form-item label="标题">
                <el-input v-model="postData.title" maxlength="30" placeholder="请输入标题" show-word-limit />
            </el-form-item>
            <el-form-item label="课程封面图">
                <el-upload ref="uploadImg" action="http://localhost:8080/uploadImage/upload" list-type="picture-card"
                    :auto-upload="false" :on-exceed="handleExceed" :limit="1" :on-success="imageUploadSuccess">
                    <el-icon>
                        <Plus />
                    </el-icon>
                    <template #file="{ file }">
                        <div>
                            <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
                            <span class="el-upload-list__item-actions">
                                <!-- <span class="el-upload-list__item-delete" @click="handleRemove(file)">
                                    <el-icon>
                                        <Delete />
                                    </el-icon>
                                </span> -->
                                <span class="el-upload-list__item-delete" @click="coverUpload">
                                    <el-icon>
                                        <Finished />
                                    </el-icon>
                                </span>
                            </span>
                        </div>
                    </template>
                </el-upload>
            </el-form-item>
            <el-form-item label="上传视频">
                <el-upload ref="uploadRef" class="upload-demo" action="http://localhost:8080/vod/upload"
                    :auto-upload="false" :on-success="handleVodUploadSuccess">
                    <template #trigger>
                        <el-button type="primary">选择文件</el-button>
                    </template>
                    <el-button class="ml-3" type="success" @click="submitUpload">
                        上传文件
                    </el-button>
                </el-upload>
            </el-form-item>
        </el-form>
        <div style="text-align: center">
            <el-button type="primary" @click.prevent="uploadCourseData()">
                确认
            </el-button>
        </div>
    </el-dialog>
</template>
<style>
.ml-3 {
    margin-left: 10px;
}
</style>