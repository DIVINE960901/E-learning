package com.example.springbootreg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class commentAdmin {
    private Integer id;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime createTime;

}
