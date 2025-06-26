import request from '@/utils/request.js'

export const findAllExamService = (params) => {
  return request.get('/exam/findAll', { params: params })
}

export const findTopicService = (params) => {
  return request.get(`/exam/findTopic?id=${encodeURIComponent(params)}`)
}

export const getDataService = (params) => {
  return request.get(`/logData/getData?examId=${encodeURIComponent(params)}`)
}

export const getDataGroupedByDateService = (params) => {
  return request.get(`/logData/getDataGroupedByDate?examId=${encodeURIComponent(params)}`)
}

export const addDataSerivce = (userId, examId, accuracy) => {
  return request.post('/logData/addData', {
    userId: userId,
    examId: examId,
    accuracy: accuracy
  })
}