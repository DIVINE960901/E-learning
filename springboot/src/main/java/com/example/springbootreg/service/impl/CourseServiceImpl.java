package com.example.springbootreg.service.impl;

import com.example.springbootreg.mapper.CourseMapper;
import com.example.springbootreg.pojo.Course;
import com.example.springbootreg.pojo.Result;
import com.example.springbootreg.pojo.courseAdmin;
import com.example.springbootreg.pojo.courseDetail;
import com.example.springbootreg.service.CourseService;
import com.example.springbootreg.service.VodService;
import com.example.springbootreg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private VodService vodService;

    @Override
    public void add(Course course) {
        course.setCreateTime(LocalDateTime.now());
        course.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        course.setCreateUser(userId);
        try {
            String URL = vodService.getPlayUrl(course.getId());
            course.setContentUrl(URL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        courseMapper.add(course);
    }
    @Override
    public List<Course> findByCreateId(Integer id) {
        List<Course> c = courseMapper.findByCreateId(id);
        return c;
    }

    @Override
    public List<courseAdmin> findByState() {
        String state = "已通过";
        List<courseAdmin> result = courseMapper.findByState(state);
        return result;
    }

    @Override
    public String findCourseUrl(String id) {
        String URL = courseMapper.findById(id);
        return URL;
    }

    @Override
    public List<courseDetail> associationFindbyCreateId(Integer id) {
        List<courseDetail> result = courseMapper.findAssociation(id);
        return result;
    }

    @Override
    public Boolean delete(String id) {
        courseMapper.deleteById(id);
        return true;
    }

    @Override
    public String deleteImage(String id) {
        String imageUrl = courseMapper.findImageById(id);
        String result = imageUrl.replace("http://localhost:8080/cover/", "");
//        System.out.println(imageName);
        return result;
    }

    @Override
    public courseDetail findById(String videoId) {
        courseDetail result = courseMapper.findPlayDataById(videoId);
        return result;
    }

    @Override
    public void setNOV(String videoId) {
        Integer NOV = courseMapper.findNOVbyId(videoId);
        Integer newNOV = NOV+1;
        courseMapper.setNOV(videoId,newNOV);
    }

    @Override
    public String getVideoCreateId(String videoId) {
        String result = courseMapper.getVideoCreateId(videoId);
        return result;
    }


}
