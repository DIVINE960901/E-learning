package com.example.springbootreg.service;


import org.springframework.web.multipart.MultipartFile;

public interface imageService {
    String uploadImage(MultipartFile file);
}
