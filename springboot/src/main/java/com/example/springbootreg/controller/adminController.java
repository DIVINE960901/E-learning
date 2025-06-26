package com.example.springbootreg.controller;


import com.example.springbootreg.pojo.*;
import com.example.springbootreg.utils.JwtUtil;
import com.example.springbootreg.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.springbootreg.service.adminService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Admin")
@Validated
public class adminController {

    @Autowired
    private adminService adminService;



    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        String identity = "admin";
        User loginUser = adminService.findByUserName(username,identity);
        if (loginUser == null) {
            return Result.error("用户名错误");
        }
        if (password.equals(loginUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
//            claims.put("identity", loginUser.getIdentity());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/allCourse")
    public Result allCourse(){
        List<Course> course = adminService.findAllCourse();
        return Result.success(course);
    }

    @GetMapping("/findState")
    public Result findState(){
        Map<String, Object> map = ThreadLocalUtil.get();

        List<courseAdmin> course = adminService.findByState();
        return Result.success(course);
    }

    @GetMapping("/selectAllComment")
    public Result selectAllComment(){
        List<commentAdmin> c= adminService.findAllComment();
        return Result.success(c);
    }


    @PutMapping("/updataState")
    public Result updataState(@RequestBody Course course){

        adminService.updataState(course);
        return Result.success();
    }

    @GetMapping("/getAllUserInfo")
    public Result<List<User>> getAllUserInfo(){
        List<User> result = adminService.findAllUser();
        return Result.success(result);
    }

    @PutMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody User user){
        adminService.update(user);
        return Result.success();
    }

    @DeleteMapping("/deleteUser")
    public Result deleteUser(@RequestParam Integer id){
        String identity = adminService.findById(id);
        if (identity.equals("admin")){
            return Result.error("不能删除管理员");
        }else {
            adminService.deleteUser(id);
            return Result.success();
        }
    }

    @PutMapping("/updataCarousl")
    public Result updataCourseL(@RequestBody List<Carousel> carousel){
        for (int i = 0; i < carousel.size(); i++){
            adminService.updataCarousl(carousel.get(i),i);
        }


        return Result.success();
    }


    @GetMapping("/getCarousel")
    public Result<List<Carousel>> getCarousel(){
        List<Carousel> result = adminService.getAllCarousel();
        return Result.success(result);
    }

    @PutMapping("/updataRecommend")
    public Result updataRecommend(@RequestBody List<courseAdmin> recommend){
        for (int i = 0; i < recommend.size(); i++){
            adminService.updataRecommend(recommend.get(i),i);
        }
        return Result.success();
    }

    @GetMapping("/getRecommend")
    public Result<List<Recommend>> getRecommend(){
        List<Recommend> result = adminService.getRecommend();
        return Result.success(result);
    }

    @GetMapping("/findExam")
    public Result findExam(){
        List<exam> result = adminService.findAllExam();
        return Result.success(result);
    }
}
