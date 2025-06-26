package com.example.springbootreg.pojo;

import lombok.Data;

@Data
public class topic {
    private Integer id;
    private String topicTitle;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private Integer examId;

}
