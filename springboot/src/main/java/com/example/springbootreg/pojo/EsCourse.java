package com.example.springbootreg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EsCourse {
    private String id;
    private String title;
    private String nickname;
    private String coverImg;
}
