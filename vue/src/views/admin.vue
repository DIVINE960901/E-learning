<script setup>
import { ref } from 'vue'
import adminHeader from '@/components/adminHeader.vue';
import adminAside from '@/components/adminAside.vue';
import useUserInfoStore from "@/stores/userinfo.js"
import router from '@/router';
import { RouterView } from 'vue-router';
const userInfoStore = useUserInfoStore()
const checkIdentity = ()=>{
  if (userInfoStore.info.identity != 'admin') {
    console.log('我不是管理员');
    router.push('/login')
  }else{
    console.log('我是管理员');
  }
  
}
checkIdentity()
const asideSettings = ref({
    isCollapse:true,
    width:'200'
})

// 给子组件绑定事件，通过子组件emit从而改变父组件的值
const changeAside = ()=>{
   asideSettings.value.isCollapse = !asideSettings.value.isCollapse
   if(asideSettings.value.isCollapse){
    asideSettings.value.width = "64"
   }else{
    asideSettings.value.width = "200"
   }
}
//console.log(userInfoStore.info.identity);
</script>
<template>

  <el-container style="min-height: 98vh;min-width: 95vw;overflow: hidden;">
    <adminAside :collapse="asideSettings.isCollapse" :width="asideSettings.width"></adminAside>
    <el-container style="height: 98vh;width:100%;display: flex;flex-direction: column;">
      <adminHeader :isCollapse="asideSettings.isCollapse"  @changeAside="changeAside"></adminHeader>
      <el-main>
        <div>
         <el-card>
          <RouterView/>
         </el-card>
        </div>
      </el-main>
    </el-container>
  </el-container>


</template>
<style scoped>
body{
  background-color: #eee;
  max-width: 100vw;
  overflow: hidden;
}

.el-main {
  width: 100%;
  padding: 0;
  height: 100vh;
  overflow-x: hidden;
  
}

</style>