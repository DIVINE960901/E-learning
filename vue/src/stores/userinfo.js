import { defineStore } from "pinia";
import { ref } from "vue";
const useUserInfoStore = defineStore('userInfo',()=>{
    const info = ref({})
    const setInfo = (newInfo)=>{
        info.value = newInfo
    }
    const removeInfo = ()=>{
        console.log("setInfo");
        info.value = {}
    }
    return {
        info,
        setInfo,
        removeInfo
    }
},{persists:true})

export default useUserInfoStore