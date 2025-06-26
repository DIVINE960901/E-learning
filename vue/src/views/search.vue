<script setup>
import { ref,watch } from 'vue'
import { searchService } from '@/api/course.js'
import headpart from '../components/headpart.vue'
import courseView from '../components/courseView.vue'
import { useRoute } from 'vue-router';
const route = useRoute();
const Data = ref([
    {id:'',
    coverImg:'',
    title:'',
    nickname:''}
])

const search = async(TargetName)=>{
    let result = await searchService(TargetName)
    Data.value = result.data
}
watch(
  () => route.params.title,
  async (newTitle) => {
    await search(newTitle);
  },
  { immediate: true }
);
search(route.params.title)
</script>

<template>
    <el-container style="max-width: 1500px; margin: auto;">
        <el-header>
            <headpart />
        </el-header>
        <el-main style="margin-top: 20px;">
            <el-space wrap size="large">
                <courseView v-for="i in Data" :key="i.id" :id="i.id" :coverImg="i.coverImg" :title="i.title"
                    :createUser="i.nickname" />
            </el-space>
        </el-main>
        <el-footer style="background-color: black; height: 230px; margin-top: 20px;">

        </el-footer>
    </el-container>
</template>


<style scoped></style>
