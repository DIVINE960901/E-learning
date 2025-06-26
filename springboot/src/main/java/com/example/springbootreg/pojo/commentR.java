package com.example.springbootreg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class commentR {
    private Integer id;
    private String nickname;
    private String userPic;
    private String content;
    private LocalDateTime createTime;

}
