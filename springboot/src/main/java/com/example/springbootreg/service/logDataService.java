package com.example.springbootreg.service;

import com.example.springbootreg.pojo.LogDateCount;
import com.example.springbootreg.pojo.logData;

import java.util.List;

public interface logDataService {
    void addData(Integer userId, Integer examId, float accuracy);

    float findByExamId(Integer examId);

    List<LogDateCount> getLogDataGroupedByDate(Integer examId);
}
