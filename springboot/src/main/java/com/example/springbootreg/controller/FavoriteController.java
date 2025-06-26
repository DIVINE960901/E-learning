package com.example.springbootreg.controller;

import com.example.springbootreg.pojo.Favorite;
import com.example.springbootreg.pojo.Result;
import com.example.springbootreg.pojo.favoriteResult;
import com.example.springbootreg.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@CrossOrigin(origins = "*")
public class FavoriteController {
    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/add")
    public Result add(@RequestBody Favorite favorite){
        Favorite fe = favoriteService.findByVideoId(favorite);
        if(fe == null){
            favoriteService.add(favorite);
            return Result.success();

        }else {
            return Result.successMsg("已经收藏过了");
        }

    }

    @GetMapping("/Fdetail")
    public Result Fdetail(){
        List<favoriteResult> r =  favoriteService.fDetail();
        return Result.success(r);
    }

    @DeleteMapping("/deleteF")
    public Result delete(@RequestParam Integer userId, @RequestParam String videoId){
        favoriteService.delete(userId, videoId);
        return Result.success();
    }
}
