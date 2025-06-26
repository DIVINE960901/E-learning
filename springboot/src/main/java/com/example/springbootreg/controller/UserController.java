package com.example.springbootreg.controller;

import com.example.springbootreg.pojo.Result;
import com.example.springbootreg.pojo.User;
import com.example.springbootreg.service.UserService;
import com.example.springbootreg.utils.JwtUtil;
import com.example.springbootreg.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$", message = "用户名必须为5到16位非空白字符") String username,@Pattern(regexp = "^\\S{5,16}$", message = "密码必须为5到16位非空白字符") String password){

            User u = userService.findByUserName(username);
            if (u==null){
                userService.register(username,password);
                return Result.success();
            }else {
                return Result.error("用户名已存在");
            }



    }

    @PostMapping ("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$", message = "用户名必须为5到16位非空白字符") String username,@Pattern(regexp = "^\\S{5,16}$", message = "密码必须为5到16位非空白字符") String password){
        User loginUser = userService.findByUserName(username);
        if(loginUser==null){
            return Result.error("用户名错误");
        }
        if(password.equals(loginUser.getPassword())){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            claims.put("identity",loginUser.getIdentity());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/){
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }


    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) ||!StringUtils.hasLength(rePwd)){
            return Result.error("参数不能为空");
        }
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser =  userService.findByUserName(username);
        if(!loginUser.getPassword().equals(oldPwd)){
            return Result.error("旧密码错误");
        }
        if(!rePwd.equals(newPwd)){
            return Result.error("新密码与旧密码不相同");
        }

        userService.updatePwd(newPwd);
        return Result.success();
    }
}
