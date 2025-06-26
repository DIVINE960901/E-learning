import request from '@/utils/request.js'

export const adminLoginService = (loginData) => {
  const params = new URLSearchParams()
  for(let key in loginData){
      params.append(key,loginData[key])
  }
  return request.post('/Admin/login',params)
}

export const getAllCourseService = () => {
  return request.get('/Admin/allCourse')
}

export const findStateService = () => {
  return request.get('/Admin/findState')
}

export const updateStateService = (stateData) => {
  return request.put('/Admin/updataState',stateData)
}

export const getAllCommentService = () => {
  return request.get('/Admin/selectAllComment')
}

export const delCommentService = (commentId) => {
  return request.delete('/comment/delComment?id='+commentId)
}

export const getAllUserService = () => {
  return request.get('/Admin/getAllUserInfo')
}

export const updateUserService = (UserData) => {
  return request.put('/Admin/updateUserInfo',UserData)
}

export const deleUserService = (userId) => {
  return request.delete('/Admin/deleteUser?id='+userId)
}

export const getCourseLDataService = () => {
  return request.get('/Admin/getCourseLDaTa')
}

export const updataCarouslService = (courseData) => {
  return request.put('/Admin/updataCarousl',courseData)
}

export const getCarouselService = () => {
  return request.get('/Admin/getCarousel')
}

export const updataRecommendService = (courseData) => {
  return request.put('/Admin/updataRecommend',courseData)
}

export const getRecommendService = () => {
  return request.get('/Admin/getRecommend')
}

export const findExamService = () =>{
  return request.get('/Admin/findExam')
}

