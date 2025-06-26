package com.example.springbootreg.service;

import com.example.springbootreg.pojo.*;

import java.util.List;

public interface adminService {
    User findByUserName(String username,String identity);

    List<Course> findAllCourse();

    List<courseAdmin> findByState();

    List<commentAdmin> findAllComment();

    void updataState(Course course);


    List<User> findAllUser();

    void update(User user);

    void deleteUser(Integer id);

    void updataCarousl(Carousel carousel,Integer id);

    List<Carousel> getAllCarousel();

    void updataRecommend(courseAdmin recommend, int i);

    List<Recommend> getRecommend();

    String findById(Integer id);

    List<exam> findAllExam();
}
