package com.example.springbootreg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Integer id;
    private Integer userId;
    private String videoId;
    private String content;
    private LocalDateTime createTime;

}
