package com.example.springbootreg.controller;

import com.example.springbootreg.pojo.*;
import com.example.springbootreg.service.CourseService;
import com.example.springbootreg.service.ElasticsearchService;
import com.example.springbootreg.service.VodService;
import com.example.springbootreg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private VodService vodService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @GetMapping("/search")
    public Result search(@RequestParam String title) throws IOException {
        List<EsCourse> result = elasticsearchService.search(title);
        return Result.success(result);
    }

    @PostMapping("/addCourse")
    public Result add(@RequestBody @Validated Course course){
        courseService.add(course);
        return Result.success();
    }

    @PostMapping("/findCourseUrl")
    public Result findCourseUrl(@RequestBody Course course){
        try {
            String url = courseService.findCourseUrl(course.getId());
            String PlayAuth = vodService.getPlayAuth(course.getId());
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("source", url);
            resultMap.put("PlayAuth", PlayAuth);
            return Result.success(resultMap);
        } catch (Exception e) {
            return Result.error("获取失败");
        }

    }

    @GetMapping("/detail")
    public Result courseInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<Course> course = courseService.findByCreateId(id);
        return Result.success(course);
    }

    @GetMapping("/getUploadCourse")
    public Result getUploadCourseInfo(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        List<courseDetail> courseDetail = courseService.associationFindbyCreateId(id);
        return Result.success(courseDetail);
    }


    @GetMapping("/All")
    public Result Allcourse(){
        Map<String, Object> map = ThreadLocalUtil.get();

        List<courseAdmin> course = courseService.findByState();
        return Result.success(course);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam String id){
        Boolean result = courseService.delete(id);
        if (result) {
            try{
                vodService.DeleteVideo(id);
                String imageName = courseService.deleteImage(id);
                String staticResourcePath = "E:/IdeaCode/springbootReg/src/main/resources/static/coverImg/";
                File imageFile = new File(staticResourcePath + imageName);
                if (imageFile.exists()) {
                    boolean isDeleted = imageFile.delete();
                    if (isDeleted) {
                        System.out.println("图片成功删除");
                    } else {
                        System.err.println("图片删除失败");
                    }
                } else {
                    System.err.println("图片不存在");
                }
            }catch (Exception e){
                System.out.println("数据删除失败");
            }
        }
        return Result.success();
    }

    @GetMapping("getVideoPlay")
    public Result getVideoPlay(@RequestParam String videoId){
        try {
            String url = vodService.getPlayUrl(videoId);
            String PlayAuth = vodService.getPlayAuth(videoId);
            courseDetail cD = courseService.findById(videoId);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("PlayAuth", PlayAuth);
            resultMap.put("source", url);
            resultMap.put("title", cD.getTitle());
            resultMap.put("nickname", cD.getNickname());
            return Result.success(resultMap);
        } catch (Exception e) {
            return Result.error("获取失败");
        }
    }

    @PostMapping("/setNOV")
    public Result setNOV(@RequestParam String videoId){
        try{
            courseService.setNOV(videoId);
            return Result.success();
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("设置失败");
        }

    }

    @GetMapping("/getVideoCreateId")
    public Result getVideoCreateId(@RequestParam String videoId){
        try{
            String result = courseService.getVideoCreateId(videoId);
            return Result.success(result);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error("获取失败");
        }
    }

}
