<script>
import { ref } from 'vue'
import { getPlayService } from '@/api/course.js'


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
    props:{
        url:String
    },
    setup(props) {
        const s = (props.url)
        return{
            s
        }
    },
    mounted() {
        //console.log(this.s);
        const initPlayer = async()=>{
            let result = await getPlayService(this.s);
            playerData.value = result.data;





            new Aliplayer({
            id: 'J_prismPlayer',
            //vid: '502a211cb04d71eea97f5017f0e80102', // 视频id
            //playauth: this.playAuth, // 播放凭证
            PlayAuth: playerData.value.PlayAuth ,
            encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
            width: '100%',
            height: '500px',
            source: playerData.value.source,
            // 以下可选设置
            //cover: 'http://outin-66c694b0b04b11ee84df00163e31af8a.oss-cn-shanghai.aliyuncs.com/c08be920ce3171eea1846733a78e0102/snapshots/b831f770fbb945f8b4235ebbb7e2c4e9-00005.jpg?Expires=1708617218&OSSAccessKeyId=LTAIxSaOfEzCnBOj&Signature=J%2B7QDWWHzn6gUaZokavoBg1XNFo%3D', // 设置视频封面
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
        });
        }
        initPlayer();

    },
    
};

</script>

<template>
    <div style="max-width: 1500px; margin: auto;">
        <div id="J_prismPlayer" class="prism-player"></div>
    </div>
</template>

<style scoped></style>