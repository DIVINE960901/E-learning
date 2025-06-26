<script>
import Command from '../components/command.vue';
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router';
import { getPlayService, setNovService,addvideoRecordService } from '@/api/course.js'
import headpart from '../components/headpart.vue';

function saveTime(memoryVideo, currentTime) {
    localStorage.setItem(memoryVideo, currentTime);
}
function getTime(memoryVideo) {
    /* return返回的是自定义起播时间  */
    return localStorage.getItem(memoryVideo)
}

const playerData = ref([])


export default {
    layout: 'video',
    setup() {
        const videoName = ref('');
        const videoAuthor = ref('');
        const videoId = ref('');

        return{
            videoName,
            videoAuthor,
            videoId,

        }
    },
    
    mounted() {
        const route = useRoute();
//        console.log('视频ID:', route.params.videoId);
        this.videoId = route.params.videoId
        // const commentList = async()=>{
        //     let result = await getCommentService(route.params.videoId)
        //     data.value = result.data
        //     console.log(data.value);
        // }
        const addRecord = async() =>{
            await addvideoRecordService(route.params.videoId)
        }
        const setNOV = async () => {
            let result = await setNovService(route.params.videoId)
            console.log(result);
        }
        const initPlayer = async () => {

            let result = await getPlayService(route.params.videoId);
//            let commentResult = await getCommentService(route.params.videoId)
            
            playerData.value = result.data;
            this.videoAuthor = playerData.value.nickname;
            this.videoName = playerData.value.title;

            new Aliplayer({
                id: 'J_prismPlayer',
                //vid: '502a211cb04d71eea97f5017f0e80102', // 视频id
                //playauth: this.playAuth, // 播放凭证
                PlayAuth: playerData.value.PlayAuth,
                encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
                width: '1150px',
                height: '500px',
                source: playerData.value.source,
                // 以下可选设置
                //cover: 'http://outin-66c694b0b04b11ee84df00163e31af8a.oss-cn-shanghai.aliyuncs.com/502a211cb04d71eea97f5017f0e80102/snapshots/56cb3992211847e18ac79de8b6407b3f-00005.jpg?Expires=1707825391&OSSAccessKeyId=LTAIxSaOfEzCnBOj&Signature=Imm9BkNs2jBfbp%2FSQTrtEbPUPrQ%3D', // 设置视频封面
                //qualitySort: 'asc', // 清晰度排序
                // mediaType: 'video', // 返回音频还是视频
                autoplay: false, // 自动播放
                isLive: false, // 直播
                rePlay: false, // 循环播放
                playsinline: true,
                preload: true,
                controlBarVisibility: 'hover', // 控制条的显示方式：鼠标悬停
                useH5Prism: true, // 播放器类型：html5
                components: [{
                    name: 'MemoryPlayComponent',
                    type: AliPlayerComponent.MemoryPlayComponent,
                    /* Set the first parameter to true to enable auto play. The default is false. */
                    args: [false, getTime, saveTime]
                }],
            }, function (player) {
                console.log('播放器创建成功');
                // 可以在这里添加额外的事件监听器或方法。
                player.on('play', function () {
                    console.log('视频开始播放');
                    setNOV()
                    addRecord()
                });
            });

        }
        initPlayer();
        

    },
    components: { Command, headpart }
};

</script>

<template>
    <el-container style="max-width: 1200px; margin: auto;">
        <el-header>
            <headpart />
        </el-header>
        <div style="margin-top: 30px;"></div>
        <el-main style="background-color: #f0f5f0;">
            <div id="J_prismPlayer" class="prism-player"></div>



        </el-main>
        <Command :videoName="videoName" :videoAuthor="videoAuthor" />
        <el-footer >

        </el-footer>
    </el-container>


    <!-- <div style="max-width: 1500px; margin: auto;">
        <div id="J_prismPlayer" class="prism-player"></div>
        <Command/>
    </div> -->
</template>

<style scoped></style>