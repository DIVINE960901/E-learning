package com.example.springbootreg.controller;


import com.example.springbootreg.pojo.Result;
import com.example.springbootreg.pojo.historyResult;
import com.example.springbootreg.pojo.videoRecord;
import com.example.springbootreg.service.VideoRecordService;
import com.example.springbootreg.utils.ThreadLocalUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/videoRecord")
@CrossOrigin(origins = "*")
public class VideoRecordController {
    @Autowired
    private VideoRecordService videoRecordService;

    @PostMapping("/addvideoRecord")
    public Result addvideoRecord(@RequestBody videoRecord videoRecord){
//        String searchVID = videoRecordService.findByVideoId(videoRecord.getVideoId());
//        System.out.println(videoRecord.getVideoId());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<historyResult> vR = videoRecordService.findbyUserId(id);
        for (int i = 0; i < vR.size(); i++) {
            if (vR.get(i).getVideoId().equals(videoRecord.getVideoId())){
//                System.out.println("存在");
                videoRecordService.updataVideoRecord(videoRecord.getVideoId());
                return Result.success();
            }
        }
        //System.out.println(vR.get(0).getVideoId());
        if (vR.isEmpty() ||( vR.size() > 0 && vR.size() < 100)) {
            videoRecordService.addvideoRecord(videoRecord);
            return Result.success();
        } else if (!vR.isEmpty()) {
            System.out.println("视频记录数量超出限制（最多允许100条）");
            historyResult earliestRecord = Collections.min(vR, Comparator.comparing(historyResult::getCreateTime));
            videoRecordService.delete(earliestRecord.getUserId(), earliestRecord.getVideoId());
            videoRecordService.addvideoRecord(videoRecord);
            return Result.success();
        } else {
            return Result.error("操作错误");
        }


    }

    @GetMapping("/videoRecordDetail")

    public Result videoRecordDetail(@RequestParam Integer userId){
        List<historyResult> vR = videoRecordService.findbyUserId(userId);
//        System.out.println(vR);
        return Result.success(vR);
    }

    @DeleteMapping("/deleteh")
    public Result delete(@RequestParam Integer userId, @RequestParam String videoId){
        videoRecordService.delete(userId, videoId);
        return Result.success();
    }
}
