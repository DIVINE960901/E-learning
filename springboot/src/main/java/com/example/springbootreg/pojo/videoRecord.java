package com.example.springbootreg.pojo;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class videoRecord {
    private Integer id;
    private Integer userId;
    private String videoId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
