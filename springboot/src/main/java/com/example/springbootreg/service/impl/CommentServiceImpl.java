package com.example.springbootreg.service.impl;

import com.example.springbootreg.mapper.CommentMapper;
import com.example.springbootreg.pojo.Comment;
import com.example.springbootreg.pojo.PageBean;
import com.example.springbootreg.pojo.commentR;
import com.example.springbootreg.service.CommentService;
import com.example.springbootreg.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public void addComment(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        comment.setUserId(userId);
        commentMapper.add(comment);
    }

    @Override
    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }

    @Override
    public List<commentR> findByVideoId(String videoId) {
        List<commentR> c = commentMapper.findByVideoId(videoId);
        return c;
    }

}
