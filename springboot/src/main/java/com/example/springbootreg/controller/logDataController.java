package com.example.springbootreg.controller;

import com.example.springbootreg.pojo.Result;
import com.example.springbootreg.pojo.logData;
import com.example.springbootreg.service.logDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/logData")
public class logDataController {

    @Autowired
    private logDataService logDataService;

    @PostMapping("/addData")
    public Result addData(@RequestBody logData logData) {
        try {
            logDataService.addData(logData.getUserId(), logData.getExamId(), logData.getAccuracy());
            return Result.success();
        }catch (Exception e){
            return Result.error("添加失败");
        }

    }

    @GetMapping("/getData")
    public Result getData(@RequestParam Integer examId){
    try {
        float avgResult = logDataService.findByExamId(examId);
//        double doubleResult = Math.round(avgResult * 100) / 100.0;
//        double doubleResult = (double) avgResult;
        return Result.success(avgResult);
    }catch (Exception e){
        return Result.error("没有数据");
    }

    }

    @GetMapping("/getDataGroupedByDate")
    public Result getDataGroupedByDate(@RequestParam Integer examId){
        try{
            return Result.success(logDataService.getLogDataGroupedByDate(examId));
        }catch (Exception e){
            return Result.error("没有数据");
        }
    }

}
