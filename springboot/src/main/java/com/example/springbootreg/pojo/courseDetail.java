package com.example.springbootreg.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class courseDetail {

    private String id;
    private String title;
    private String state;
    private String nickname;
    private LocalDateTime createTime;
    private Integer NumberOfCollections;
    private Integer NumberOfViewers;
}
