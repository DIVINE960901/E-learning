package com.example.springbootreg.controller;

import com.example.springbootreg.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.springbootreg.service.imageService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/uploadImage")
public class imageController {

    @Autowired
    private imageService imageService;

    @PostMapping("/upload")
    public Result uploadImage(MultipartFile file)
    {
        String result = imageService.uploadImage(file);
        return Result.success(result);
    }
}
