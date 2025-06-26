package com.example.springbootreg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class historyResult {
    private Integer userId;
    private String videoId;
    private String title;
    private String coverImg;
    private Integer createUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
