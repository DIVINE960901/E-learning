package com.example.springbootreg.service.impl;

import com.example.springbootreg.pojo.*;
import com.example.springbootreg.service.adminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.springbootreg.mapper.adminMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class adminServiceImpl implements adminService {

    @Autowired
    private adminMapper adminMapper;

    @Override
    public User findByUserName(String username,String identity) {
        User u = adminMapper.findByUserName(username,identity);
        return u;
    }

    @Override
    public List<Course> findAllCourse() {
        List<Course> result = adminMapper.findAllCourse();
        return result;
    }

    @Override
    public List<courseAdmin> findByState() {
        String state = "待审核";
        List<courseAdmin> result = adminMapper.findByState(state);
        return result;
    }

    @Override
    public List<commentAdmin> findAllComment() {
        List<commentAdmin> ca = adminMapper.findAllComment();
        return ca;
    }

    @Override
    public void updataState(Course course) {
        course.setUpdateTime(LocalDateTime.now());
        adminMapper.updataState(course);
    }

    @Override
    public List<User> findAllUser() {

        List<User> user = adminMapper.findAllUser();
        return user;
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        adminMapper.updataUser(user);
    }

    @Override
    public void deleteUser(Integer id) {
        adminMapper.deleteUser(id);

    }

    @Override
    public void updataCarousl(Carousel carousel,Integer id) {
        adminMapper.updataCarousl(carousel,id);
    }

    @Override
    public List<Carousel> getAllCarousel() {
        List<Carousel> result = adminMapper.findAllCarousel();

        return result;
    }

    @Override
    public void updataRecommend(courseAdmin recommend, int i) {
        adminMapper.updataRecommend(recommend,i);
    }

    @Override
    public List<Recommend> getRecommend() {
        List<Recommend> result = adminMapper.getRecommend();
        return result;
    }

    @Override
    public String findById(Integer id) {
        String result = adminMapper.findById(id);

        return result;
    }

    @Override
    public List<exam> findAllExam() {
        List<exam> list = adminMapper.findAllExam();
        return list;
    }


}
