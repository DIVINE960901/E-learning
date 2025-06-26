import request from '@/utils/request.js'
import { useTokenStore} from '@/stores/counter'

export const getAllCourseService = () =>{
    const tokenStore = useTokenStore()
    tokenStore.token
    console.log(tokenStore.token);
    return request.get('/course/All')
}