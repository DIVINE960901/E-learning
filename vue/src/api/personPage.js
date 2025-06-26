import request from '@/utils/request.js'

export const userUpdateService = (userData) => {
    return request.put('/user/update',userData)
}

export const updatePwdService = (pwdData) => {
    return request.patch('/user/updatePwd',JSON.stringify(pwdData), {
        headers: {
          'Content-Type': 'application/json',
        }
    })
}

export const getFdetailService = (userId) => {
//    console.log(userId);
    return request.get(`/favorite/Fdetail?userId=${encodeURIComponent(userId)}`)
}

export const deleteSubService = (userId,videoId) => {
    return request.delete(`/favorite/deleteF?userId=${encodeURIComponent(userId)}&videoId=${encodeURIComponent(videoId)}`)
}

export const gethistoryService = (userId) => {
    //    console.log(userId);
        return request.get(`/videoRecord/videoRecordDetail?userId=${encodeURIComponent(userId)}`)
} 

export const deleteHistoryService = (userId,videoId) => {
    return request.delete(`/videoRecord/deleteh?userId=${encodeURIComponent(userId)}&videoId=${encodeURIComponent(videoId)}`)
}

export const addCourseService = (courseData) => {
    // const params = new URLSearchParams()
    // for(let key in loginData){
    //     params.append(key,loginData[key])
    // }
    return request.post('/course/addCourse',courseData)
}

export const courseDetailService = () =>{

    return request.get('/course/getUploadCourse')
}

export const deleteCourseService = (courseId) => {
    return request.delete(`/course/delete?id=${encodeURIComponent(courseId)}`)
}

export const addExamService = (examData) => {
    return request.post('/exam/addExam',examData)
}

export const deleteExamService = (examId) => {
    return request.delete(`/exam/deleteExam?id=${encodeURIComponent(examId)}`)
}

export const examDetailService = () =>{
    return request.get('/exam/findExam')
}

export const addTopicService = (topicData) => {
    return request.post('/exam/addTopic',topicData)
}

export const getTopicService = (id) => {
    //console.log(id);
    return request.get(`/exam/findTopic?id=${encodeURIComponent(id)}`)
}  