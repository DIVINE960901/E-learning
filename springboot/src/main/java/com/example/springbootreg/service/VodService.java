package com.example.springbootreg.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadVideo(MultipartFile videoFile);

    String getPlayAuth(String videoId) throws Exception;

    String getPlayUrl(String videoId) throws Exception;

    void DeleteVideo(String videoId);
}
