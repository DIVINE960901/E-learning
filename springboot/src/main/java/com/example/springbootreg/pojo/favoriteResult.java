package com.example.springbootreg.pojo;

import lombok.Data;

@Data
public class favoriteResult {
    private Integer userId;
    private String videoId;
    private String title;
    private String coverImg;
    private Integer createUser;
}
