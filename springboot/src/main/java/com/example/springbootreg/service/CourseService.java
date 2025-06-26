package com.example.springbootreg.service;

import com.example.springbootreg.pojo.Course;
import com.example.springbootreg.pojo.Result;
import com.example.springbootreg.pojo.courseAdmin;
import com.example.springbootreg.pojo.courseDetail;

import java.util.List;

public interface CourseService {
    void add(Course course);



    List<Course> findByCreateId(Integer id);

    List<courseAdmin> findByState();

    String findCourseUrl(String id);

    List<courseDetail> associationFindbyCreateId(Integer id);

    Boolean delete(String id);

    String deleteImage(String id);

    courseDetail findById(String videoId);

    void setNOV(String videoId);

    String getVideoCreateId(String videoId);
}
