package com.example.springbootreg.pojo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LogDateCount {
    private LocalDate dateGroup;
    private int totalCount;
}
