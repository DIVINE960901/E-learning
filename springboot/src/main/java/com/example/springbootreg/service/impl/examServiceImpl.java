package com.example.springbootreg.service.impl;

import com.example.springbootreg.pojo.PageBean;
import com.example.springbootreg.pojo.exam;
import com.example.springbootreg.pojo.topic;
import com.example.springbootreg.service.examService;
import com.example.springbootreg.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springbootreg.mapper.examMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class examServiceImpl implements examService {

    @Autowired
    examMapper examMapper;

    @Override
    public void add(exam exam) {
        exam.setCreateTime(LocalDateTime.now());
        exam.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
//        System.out.println(userId);
        exam.setUserId(userId);
//        System.out.println(exam.getSingleNumber());
        examMapper.add(exam);
    }

    @Override
    public List<exam> findByUserId(Integer id) {
        List<exam> list = examMapper.findByUserId(id);
        return list;
    }

    @Override
    public void addTopic(topic topic) {
        examMapper.addTopic(topic);
    }

    @Override
    public List<topic> findByExamId(Integer examId) {
        List<topic> result = examMapper.findByExamId(examId);
        return result;
    }

    @Override
    public Boolean deleteById(Integer id) {
        try {
            examMapper.deleteById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public PageBean<exam> findAll(Integer pageNum, Integer pageSize) {
        PageBean<exam> pb = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<exam> result = examMapper.findAll();
        Page<exam> p = (Page<exam>) result;
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }


}
