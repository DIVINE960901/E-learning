<script setup>
import { ref,onMounted,watch } from 'vue'
import useUserInfoStore from "@/stores/userinfo.js"
import { getCommentService,addCommentService,addSubscribeService,getVideoCreateService } from '@/api/course.js'
import useChatInfoStore from "@/stores/chatInfo.js"
import { useRoute } from 'vue-router';
import router from '@/router';
const userInfoStore = useUserInfoStore()
const route = useRoute();
 const getProps = defineProps(['videoName','videoAuthor'])
//const getProps = defineProps(['videoName', 'videoAuthor', 'videoId', 'Cdata']);
const commentList = async()=>{
    let result = await getCommentService(route.params.videoId)
    data.value = result.data
    // console.log(result.data);
}
commentList()

let loadedItemsCount = ref(8);
const loadIncrement = 5;
const Commandtext = ref('');
const data = ref([])

const chatInfoTo = useChatInfoStore()
const getVideoCreate = async()=>{
    let result = await getVideoCreateService(route.params.videoId)
    chatInfoTo.setChatToInfo(result.data)
    chatInfoTo.setchatToNickname(getProps.videoAuthor)

}


//上面的是后台传递过来的数据
//const count = ref(data.value.slice(0, loadedItemsCount.value));
const count = ref([]);

// 添加一个 watch 监听器，当 data.value 发生变化时，同步更新 count
watch(data, (newData) => {
  count.value = newData.slice(0, loadedItemsCount.value);
}, { deep: true });

const load = () => {
    const remainingItems = data.value.length - loadedItemsCount.value;
    
    const nextItemsToLoad = Math.min(loadIncrement, remainingItems);
    const nextItems = data.value.slice(loadedItemsCount.value, loadedItemsCount.value + nextItemsToLoad);
    count.value.push(...nextItems);
    
    loadedItemsCount.value += nextItemsToLoad;
  

}

const subscribeC = async()=>{
    let result = await addSubscribeService(route.params.videoId)
    console.log(result.message);
    if(result.message === "已经收藏过了"){
        ElMessage.warning('已经收藏过了')
    }else{
        ElMessage.success('收藏成功')
    }
}

const question = () =>{
    getVideoCreate()
    router.push('/chatSystem')
}
const getCommand = async() => {
    const postComment = {
        videoId: route.params.videoId,
        content: Commandtext.value
    }
    let result = await addCommentService(postComment)
    ElMessage.success(result.message? result.message:'发布成功')
    let Refresh = await getCommentService(route.params.videoId)
    data.value = Refresh.data
    count.value = data.value.slice(0, loadedItemsCount.value);
    loadedItemsCount.value += loadIncrement;
    // console.log(data.value);
    // console.log(count.value);
    // console.log(loadIncrement);
    // console.log(loadedItemsCount.value);
    Commandtext.value = '';
    
}
</script>
<template>
    <div class="videoContent" style="background-color: #f0f5f0;">
        <div>
            <div style="width: 870px; padding-left: 40px; margin-top: 40px;">
                <el-container>
                    <el-aside style="margin-top: 25px;">
                        <img :src="userInfoStore.info.userPic">
                    </el-aside>
                    <el-main>
                        <el-input v-model="Commandtext" :autosize="{ minRows: 5, maxRows: 8 }" type="textarea"
                            placeholder="写下你的评论" />
                    </el-main>
                </el-container>
                <el-container>
                    <el-footer class="sendButton">
                        <el-button type="primary" @click="getCommand()" size="large">发布</el-button>
                    </el-footer>
                </el-container>
            </div>
            <div style="margin: 20px 0"></div>
            <ul v-infinite-scroll="load" >
                <el-space direction="vertical" :size="20">
                    <el-card v-for="i in count" :key="i.id" style="width: 850px;">
                        <el-container>
                            <el-aside>
                                <div>
                                    <img :src="i.userPic">
                                </div>                               
                            </el-aside>
                            <el-container>
                                <el-header>
                                    <h4>{{ i.nickname }}</h4>
                                </el-header>
                                <el-main>
                                    <p class="content">{{ i.content }}</p>
                                </el-main>
                            </el-container>
                        </el-container>
                    </el-card>
                </el-space>
            </ul>
        </div>
        <div style="flex:250px;">
            <el-descriptions direction="vertical" size="large" :column="2" border
                style="margin-top: 60px; height: auto; flex: 1; width: 260px;">
                <el-descriptions-item label="视频名称" label-align="center" align="center"
                    width="335px">{{ getProps.videoName  }}</el-descriptions-item>
                <el-descriptions-item label="视频作者" label-align="center" align="center"
                    width="335px">{{ getProps.videoAuthor }}</el-descriptions-item>
            </el-descriptions>
            <div style="display: flex; margin-top: 20px;margin-left: 55px;">
                <el-button type="primary" size="large" @click="subscribeC()">
                    收藏
                </el-button>
                <el-button type="primary" size="large" @click="question()">
                    提问
                </el-button>
            </div>
        </div>
    </div>
</template>
<style scoped>
.videoContent {
    display: flex;
    justify-content: space-between;
    width: 100%;
}

.ul {
    padding-left: 10px;
}

.content {
    word-wrap: break-word;
    overflow-wrap: break-word;
    white-space: normal;
}

.el-aside {
    width: 100px;
    height: 100px;
}

.el-header {
    height: 15px;
}

.el-header>h4 {
    margin-top: 10px;
}

.sendButton {
    text-align: right;
    width: 100%;
}

.video-description-auto-wrap {
    word-wrap: break-word;
    overflow-wrap: break-word;
    white-space: normal;
}


/*.el-main>div {
    width: 100%;
}*/
</style>