import axios from 'axios';
import router from '@/router';
import { useTokenStore } from '@/stores/counter';
import { ElMessage } from 'element-plus';
import * as jwt_decode from 'jwt-decode';
//定义一个变量,记录公共的前缀  ,  baseURL
const baseURL = 'http://localhost:8080';
// const baseURL = 'api';
const instance = axios.create({baseURL})


//请求拦截器
instance.interceptors.request.use(
    config=>{
        const tokenStore = useTokenStore();
        if(tokenStore.token){
            config.headers.Authorization = tokenStore.token;
        }
        return config;
    },
    err=>{
        //请求失败
        return Promise.reject(err);
    }
)

//响应拦截器
instance.interceptors.response.use(
    result=>{
        if(result.data.code === 0){
            return result.data;
        }
        
        ElMessage.error(result.data.message?result.data.message:'服务异常');
        console.log(result.data);
        return Promise.reject(result.data)
    },
    // err=>{
    //     alert('服务异常');
    //     return Promise.reject(err);//异步的状态转化成失败的状态
    // },
    // err =>{
    //     if(err.response.status === 401){
    //         alert('请先登录');
    //         router.push({ name: 'home' });
            
    //     }else{
    //         ElMessage.error('服务异常');
    //     }
        
    //     return Promise.reject(err);
    // }
    err => {
        if (err.response.status === 401) {
            const tokenStore = useTokenStore()
            const token = tokenStore.token
            try {
                const decodedToken = jwt_decode(token);
                
                // 根据Token中的exp字段判断是否已过期
                const now = Date.now() / 1000;
                const isTokenExpired = decodedToken.exp < now;

                if (isTokenExpired) {
                    useTokenStore().removeToken();
                    ElMessage.error('登录信息已过期，请重新登录');
                    router.push({ name: 'login' });
                } else {
                    ElMessage.error('请先登录');
                    // 对于非Token过期导致的401错误，此处不执行跳转操作
                }
            } catch (decodeError) {
                tokenStore.removeToken();
                ElMessage.error('登录信息无效，请重新登录');
                router.push({ name: 'home' });
            }
        } else {
            ElMessage.error('服务异常2');
        }

        return Promise.reject(err);
    }
)

export default instance;