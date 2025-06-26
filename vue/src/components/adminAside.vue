<script setup>
import { ref } from 'vue'
import { House, Menu, Files, User, ChatRound, Wallet } from '@element-plus/icons-vue'
const settings = defineProps({
  collapse: Boolean,
  width: String
})

const iconColor = ref('color:#F8FAFB')
</script>


<template>
  <el-aside :width="settings.width">
    <el-scrollbar>
      <el-menu :default-openeds="['1', '3']" :collapse="settings.collapse" active-text-color="#F8FAFB"
        background-color="#1A1C1E" text-color="#74767A" :router="true">
        <!-- <el-menu-item index="/admin">
          <el-icon>
            <House />
          </el-icon>
          <template #title>
            <span>首页</span>
          </template>
        </el-menu-item> -->
        <el-sub-menu index="2">
          <template #title>
            <el-icon>
              <Menu />
            </el-icon><span>课程信息管理</span>
          </template>
          <el-menu-item index="/admin/CourseControl">课程管理</el-menu-item>
          <el-menu-item index="/admin/courseReview">课程审核</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/admin/commandControl">
          <el-icon>
            <ChatRound />
          </el-icon>
          <template #title>
            <span>评论管理</span>
          </template>
        </el-menu-item>
        <el-sub-menu index="4">
          <template #title>
            <el-icon>
              <Files />
            </el-icon><span>首页内容</span>
          </template>
          <el-menu-item index="/admin/carouselControl">轮播图管理</el-menu-item>
          <el-menu-item index="/admin/Recommended">推荐课程管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="5">
          <template #title>
            <el-icon>
              <User />
            </el-icon><span>用户管理</span>
          </template>
          <el-menu-item index="/admin/userControl">用户管理列表</el-menu-item>
        </el-sub-menu>
        <!-- <el-sub-menu index="/admin/examControl">
          <template #title>
            <el-icon>
              <setting />
            </el-icon><span>试题管理</span>
          </template>
        </el-sub-menu> -->
          <el-menu-item index="/admin/examControl">
            <template #title>
              <el-icon>
                <Wallet />
              </el-icon><span>试题管理</span>
            </template>
          </el-menu-item>
      </el-menu>
    </el-scrollbar>
  </el-aside>
</template>


<style scoped>
.el-aside {
  height: 98vh;
  overflow: hidden;
  box-shadow: var(--el-box-shadow-lighter);
}

.el-scrollbar {
  height: 100vh;
  overflow: hidden;
  background-color: #1A1C1E;
}

.el-menu {

  border-right: none;
}
</style>
