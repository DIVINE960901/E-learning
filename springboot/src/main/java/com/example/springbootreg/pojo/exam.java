package com.example.springbootreg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class exam {
    private Integer id;
    private String title;
    private Integer userId;
    private Integer singleNumber;
    private String nickname;
    private String describe;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
