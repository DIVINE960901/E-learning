<!-- <script>
export default {
    data() {
        return {
            fileList: [],//上传文件列表
            BASE_API: "http://localhost:8003", // 接口API地址
            video: {// 课时对象
                title: '',
                videoSourceId: '',
                videoOriginalName: ''
            }
        }
    },
    methods: {
        handleUploadExceed(file, fileList) {
            this.$message.warning('想要重新上传视频,请先删除已经上传的视频')
        },
        //点击确定调用的方法
        handleVodRemove() {
            video.removeAlyVideo(this.video.videoSourceId).then(response => {
                this.$message({
                    type: 'success',
                    message: '删除视频成功!'
                })
                //把文件列表清空
                this.fileList = []
                //删除的视频信息不再存入数据库
                this.video.videoSourceId = ''
                this.video.videoOriginalName = ''
            })

        },
        //点击×调用的方法
        // beforeVodRemove(file, fileList) {
        //     return this.$confirm(`确定移除 ${file.name}?`)
        //     :before-remove="beforeVodRemove"
        // },
        //上传成功方法
        handleVodUploadSuccess(response, file, fileList) {
            this.video.videoSourceId = response.data
            this.video.videoOriginalName = file.name

        }
    }
}

</script>
<template>
    <el-form-item label="上传视频">
        <el-upload :on-success="handleVodUploadSuccess" :on-remove="handleVodRemove" 
            :on-exceed="handleUploadExceed" :file-list="fileList" :action="BASE_API + '/vod/upload'"
            :limit="1" class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
                <div slot="content">最大支持1G，<br>
                    支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                    GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                    MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                    SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
                <i class="el-icon-question" />
            </el-tooltip>
        </el-upload>
    </el-form-item>
</template>
<style></style> -->

<template>
    <el-upload ref="uploadRef" class="upload-demo" action="http://localhost:8003/vod/upload" :auto-upload="false"
        :on-success="handleVodUploadSuccess">
        <template #trigger>
            <el-button type="primary">选择文件</el-button>
        </template>
        <el-button class="ml-3" type="success" @click="submitUpload">
            上传
        </el-button>
    </el-upload>
</template>
<script setup>
import { ref } from 'vue'

// 创建一个 ref 来存储 Upload 组件实例
const uploadRef = ref(null)

// 提交上传的方法
const submitUpload = () => {
    // 在组件已挂载后调用submit方法
    if (uploadRef.value) {
        uploadRef.value.submit()
    }
}
const handleVodUploadSuccess=(response, file, fileList) =>{
    console.log(response.data);
    console.log(file.name);
    

}
</script>