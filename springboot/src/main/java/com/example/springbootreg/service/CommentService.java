package com.example.springbootreg.service;

import com.example.springbootreg.pojo.Comment;
import com.example.springbootreg.pojo.PageBean;
import com.example.springbootreg.pojo.commentR;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);

    void deleteById(Integer id);

    List<commentR> findByVideoId(String videoId);

}
