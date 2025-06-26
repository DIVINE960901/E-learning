package com.example.springbootreg.service;

import com.example.springbootreg.pojo.PageBean;
import com.example.springbootreg.pojo.exam;
import com.example.springbootreg.pojo.topic;

import java.util.List;

public interface examService {
    void add(exam exam);

    List<exam> findByUserId(Integer id);

    void addTopic(topic topic);

    List<topic> findByExamId(Integer examId);

    Boolean deleteById(Integer id);

    PageBean<exam> findAll(Integer pageNum, Integer pageSize);


}
