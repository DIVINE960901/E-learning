<script setup>
import 'element-plus/theme-chalk/display.css'
import { ref } from 'vue'
import { useRouter } from 'vue-router';
const router = useRouter();
const cardCSS = ()=>{
    return{
        padding: 0,
    }
}
// const constants = ref([
//     {id:'1',coverImg:'/images/1.PNG',title:'新版Java入门到起飞（全）',createUser:'AAA'},
//     {id:'2',coverImg:'/images/2.jpg',title:'JAVA集合',createUser:'BBB'},
//     {id:'3',coverImg:'/images/3.jpg',title:'JAVA面向对象',createUser:'CCC'},
//     {id:'4',coverImg:'/images/4.jpg',title:'Java入门到起飞',createUser:'DDD'},
//     {id:'5',coverImg:'/images/5.jpg',title:'前端入门 H5+CSS3+实战',createUser:'EEE'},
//     {id:'6',coverImg:'/images/6.jpg',title:'14天搞定JavaWeb 简洁高效 一套精通',createUser:'FFF'},
//     {id:'7',coverImg:'/images/7.jpg',title:'JAVA基础语法',createUser:'GGG'},
//     {id:'8',coverImg:'/images/8.jpg',title:'20天Java基础入门 项目+真题',createUser:'HHH'},
// v-for="i in constants" :key="i.id"])
defineProps(['id','coverImg','title','createUser'])
const getId = (id) =>{
    console.log(id);
    router.push({ name: 'course', params: { videoId: id} });
}
</script>
<template>
    <el-card  style="width: 277px;" :body-style="cardCSS()" >
            <div class="mod">
                <img class="entry-pic" :src="coverImg" />
                <div class="main">
                    <span class="info">{{title}}</span>
                    <div class="group">
                        <span class="tag">{{createUser}}</span>
                        <div class="label-wrapper">
                            <el-button 
                                class="label" 
                                type="primary" 
                                plain
                                @click="getId(id)">开始学习</el-button>
                        </div>
                    </div>
                </div>
            </div>
        </el-card>
</template>

<style>
.mod {
    /* border: 1px solid rgba(230, 230, 230, 1);这句要删 */
    background-color: rgba(255, 255, 255, 1);
    padding-top: 1px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    width: 277px;
    height: 276px;
    font-family: MiSans-Regular;
}

.el-card__body {
    padding: 0px;
}

.mod .entry-pic {
    width: 277px;
    height: 178px;
    align-self: center;
}

.mod .main {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-self: center;
    height: 74px;
    width: 277px;
    padding: 10px 0px 14px 10px;
}

.mod .info {
    color: rgba(0, 0, 0, 1);
    font-size: 16px;
    line-height: 22px;
    align-self: flex-start;
    max-width: 257px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin-left: 10px;
}

.mod .group {
    width: 257px;
    height: 26px;
    display: flex;
    margin-top: 25px;
    justify-content: flex-start;
    align-self: center;
    position: relative;
    padding: 0 11px 4px;
    /* background-image: url(./images/img_1.png); */
    background-repeat: no-repeat;
    background-position: 0px 0px;
    background-size: 100% 100%;
    background-origin: padding-box;
}

.mod .tag {
    color: rgba(0, 0, 0, 1);
    font-size: 16px;
    line-height: 22px;
    align-self: flex-start;
    max-width: 235px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.mod .label-wrapper {
    width: 100px;
    height: 25px;
    position: absolute;
    display: flex;
    justify-content: center;
    right: 0px;
    top: 0px;
    padding: 2px 12px 3px;
    background-repeat: no-repeat;
    background-position: 0px 0px;
    background-size: 100% 100%;
    background-origin: padding-box;
}

.mod .label {
    color: rgba(52, 122, 187, 1);
    font-family: SourceHanSansCN-Regular;
    font-size: 14px;
    line-height: 20px;
    align-self: flex-start;
    max-width: 76px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
</style>