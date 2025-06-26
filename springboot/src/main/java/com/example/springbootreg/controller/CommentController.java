package com.example.springbootreg.controller;

import com.example.springbootreg.pojo.Comment;
import com.example.springbootreg.pojo.PageBean;
import com.example.springbootreg.pojo.Result;
import com.example.springbootreg.pojo.commentR;
import com.example.springbootreg.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "*")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("/addComment")
    public Result addComment(@RequestBody Comment comment){
        commentService.addComment(comment);
        return Result.success();
     }

     @GetMapping("/commentDetail")
    public Result commentDetail(@RequestParam String videoId){
        List<commentR> c= commentService.findByVideoId(videoId);
        return Result.success(c);
     }


     @DeleteMapping("delComment")
    public Result delComment(@RequestParam Integer id){
        commentService.deleteById(id);
        return Result.success();
     }
}
