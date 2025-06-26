package com.example.springbootreg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class logData {

    private Integer id;
    private Integer userId;
    private Integer examId;
    private float accuracy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}
