package com.example.springbootreg.service.impl;

import com.example.springbootreg.service.imageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.UUID;

@Service
public class imageServiceImpl implements imageService {

    @Value("${file.upload-path}")
    private String imagePath;
    @Override
    public String uploadImage(MultipartFile file) {
        String fileName;

        try {
            //System.out.println(imagePath);
            String originalFilename = file.getOriginalFilename();
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf('.'));
            fileName = UUID.randomUUID().toString() + suffixName;
            //System.out.println(fileName);
            file.transferTo(new File(imagePath + fileName));
            String coverImg = "http://localhost:8080/cover/" + fileName;
            return coverImg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
