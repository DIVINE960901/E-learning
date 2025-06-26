package com.example.springbootreg.controller;

import com.example.springbootreg.pojo.PageBean;
import com.example.springbootreg.pojo.Result;
import com.example.springbootreg.pojo.exam;
import com.example.springbootreg.pojo.topic;
import com.example.springbootreg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.springbootreg.service.examService;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exam")
public class examController {

    @Autowired
    private examService examService;

    @GetMapping("/findAll")
    public Result<PageBean<exam>> findAll(Integer pageNum, Integer pageSize){
        PageBean<exam> pb = examService.findAll(pageNum, pageSize);
        return Result.success(pb);
    }

    @PostMapping("/addExam")
    public Result addExam(@RequestBody exam exam)
    {
        examService.add(exam);
        return Result.success();
    }

    @PostMapping("/addTopic")
    public Result addTopic(@RequestBody topic topic)
    {
        examService.addTopic(topic);
        return Result.success();
    }

    @GetMapping("/findExam")
    public Result findExam(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<exam> result = examService.findByUserId(id);
        return Result.success(result);
    }

    @GetMapping("/findTopic")
    public Result findTopic(@RequestParam("id") Integer examId){
        List<topic> result = examService.findByExamId(examId);
//        System.out.println(result);
        return Result.success(result);
    }

    @DeleteMapping("/deleteExam")
    public Result deleteExam(@RequestParam("id") Integer id){
        Boolean result = examService.deleteById(id);
        return Result.success(result);
    }
}
