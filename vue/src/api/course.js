import request from '@/utils/request.js'

export const getPlayService = (videoId) => {
  return request.get(`/course/getVideoPlay?videoId=${encodeURIComponent(videoId)}`)
}

export const getCommentService = (videoId) => {
  return request.get(`/comment/commentDetail?videoId=${encodeURIComponent(videoId)}`)
}

export const addSubscribeService = (videoId) => {
  return request.post('/favorite/add',{
    videoId:videoId
  })
}

export const addCommentService = (commentData) => {
  return request.post('/comment/addComment',commentData)
}

export const setNovService = (videoId)=>{
  return request.post(`/course/setNOV?videoId=${encodeURIComponent(videoId)}`)
}

export const getVideoCreateService = (videoId)=>{
  return request.get(`/course/getVideoCreateId?videoId=${encodeURIComponent(videoId)}`)
}

export const searchService = (keyword)=>{
  return request.get(`/course/search?title=${encodeURIComponent(keyword)}`)
}

export const addvideoRecordService = (videoId)=>{
    return request.post('/videoRecord/addvideoRecord',{
    videoId:videoId
  })
}