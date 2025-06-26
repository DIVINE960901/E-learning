package com.example.springbootreg.service.impl;

import com.example.springbootreg.mapper.VideoRecordMapper;
import com.example.springbootreg.pojo.historyResult;
import com.example.springbootreg.pojo.videoRecord;
import com.example.springbootreg.service.VideoRecordService;
import com.example.springbootreg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class VideoRecordServiceImpl implements VideoRecordService {
    @Autowired
    private VideoRecordMapper videoRecordMapper;
    @Override
    public void addvideoRecord(videoRecord videoRecord) {
        videoRecord.setCreateTime(LocalDateTime.now());
        videoRecord.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        videoRecord.setUserId(userId);
        videoRecordMapper.addvideoRecord(videoRecord);
    }

    @Override
    public List<historyResult> findbyUserId(Integer userId) {
        List<historyResult> videoRecords = videoRecordMapper.findbyUserId(userId);
        return videoRecords;
    }

    @Override
    public String findByVideoId(String videoId) {
        String vR = videoRecordMapper.findByVideoId(videoId);
        return vR;
    }

    @Override
    public void delete(Integer userId, String videoId) {
        videoRecordMapper.deleteById(userId,videoId);
    }

    @Override
    public void updataVideoRecord(String videoId) {

        videoRecordMapper.updataVideoRecord(videoId,LocalDateTime.now());
    }
}
