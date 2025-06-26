package com.example.springbootreg.service;
 import com.example.springbootreg.pojo.Result;
 import com.example.springbootreg.pojo.historyResult;
 import com.example.springbootreg.pojo.videoRecord;

 import java.util.List;

public interface VideoRecordService {
    void addvideoRecord(videoRecord videoRecord);

    List<historyResult> findbyUserId(Integer userId);

    String findByVideoId(String videoId);

    void delete(Integer userId, String videoId);

    void updataVideoRecord(String videoId);
}
