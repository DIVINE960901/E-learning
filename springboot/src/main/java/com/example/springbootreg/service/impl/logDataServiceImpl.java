package com.example.springbootreg.service.impl;

import com.example.springbootreg.pojo.LogDateCount;
import com.example.springbootreg.pojo.logData;
import com.example.springbootreg.service.logDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springbootreg.mapper.logDataMapper;

import java.util.List;

@Service
public class logDataServiceImpl implements logDataService {

    @Autowired
    private logDataMapper logDataMapper;

    @Override
    public void addData(Integer userId, Integer examId, float accuracy) {
        logDataMapper.addData(userId,examId,accuracy);
    }

    @Override
    public float findByExamId(Integer examId){
        try{
            float result = logDataMapper.logDataGroupby(examId);
            return result;
        }catch (Exception e){
            throw new RuntimeException("该考试没有考试记录");
        }
    }

    @Override
    public List<LogDateCount> getLogDataGroupedByDate(Integer examId) {
        try{
            List<LogDateCount> logDataGroupedByDate = logDataMapper.getLogDataGroupedByDate(examId);
            return logDataGroupedByDate;
        }catch (Exception e){
            throw new RuntimeException("该考试没有考试记录");
        }
    }
}
