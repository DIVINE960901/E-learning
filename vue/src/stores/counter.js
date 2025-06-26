import { ref} from 'vue'
import { defineStore } from 'pinia'

export const useTokenStore = defineStore('token', () => {
  const token = ref('')
  const setToken = (newtoken) => {
    token.value = newtoken
    
  }
  const getToken = () => {
    return token.value
  }
  const removeToken = () => {
    console.log('清除token');
    token.value = ''
  }
  return { token, setToken,removeToken,getToken }
},{
  persist: true
})
