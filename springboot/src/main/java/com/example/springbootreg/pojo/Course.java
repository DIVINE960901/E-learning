package com.example.springbootreg.pojo;

import com.example.springbootreg.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Course {
    private String id;
    @NotEmpty
    @Pattern(regexp = "^[\\s\\S]{1,50}$")
    private String title;

    private String contentUrl;
    @NotEmpty
    @URL
    private String coverImg;

    @State
    private String state;
    private Integer createUser;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private Integer NumberOfCollections;
    private Integer NumberOfViewers;
}
