<script>
export default {
    props: {
        message: String,
        sender:String,
        receiver:String,
        required: true
    },

};
</script>

<template>
    <div class="messageBone">
        <div  style="display: flex; align-items: flex-start;">
            <el-avatar src="/images/student.jpg" style="margin-right: 10px;" />
            <el-card shadow="never" style="border-radius: 0 10px 0 10px; width: 600px;">
                <p class="content">{{ message }}</p>
            </el-card>
        </div>
    </div>
</template>

<style scoped>
.messageBone {
    display: flex;
    align-items: center;
    gap: 10px;
    
    /* 添加间距以使 Avatar 和 Card 分开 */
}

.content {
    word-wrap: break-word;
    overflow-wrap: break-word;
    white-space: normal;
    max-width: 600px;
    /* 可调整字体大小和行高 */
    font-size: 14px;
    line-height: 1.5;

    /* 防止卡片内容区域出现滚动条 */
    padding: 20px;
    box-sizing: border-box;

    /* 背景颜色和文字颜色 */
    /* background-color: #f8f9fa; */
    color: #333;

    /* 文本对齐方式 */
    text-align: left;
}
</style>