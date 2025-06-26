package com.example.springbootreg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class courseAdmin {
    private String id;
    private String title;
    private String state;
    private String nickname;
    private String contentUrl;
    private String coverImg;
    private LocalDateTime createTime;
}
