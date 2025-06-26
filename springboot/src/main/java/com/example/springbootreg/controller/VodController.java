package com.example.springbootreg.controller;

import com.example.springbootreg.pojo.Result;
import com.example.springbootreg.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/vod")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;


    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        System.out.println("videoId: " + videoId);
        return Result.success(videoId);
    }
}
