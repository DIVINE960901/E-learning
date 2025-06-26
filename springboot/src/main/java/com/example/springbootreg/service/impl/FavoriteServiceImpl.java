package com.example.springbootreg.service.impl;

import com.example.springbootreg.mapper.FavoriteMapper;
import com.example.springbootreg.pojo.Favorite;
import com.example.springbootreg.pojo.favoriteResult;
import com.example.springbootreg.service.FavoriteService;
import com.example.springbootreg.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteMapper favoriteMapper;

    @Override
    public void add(Favorite favorite) {
        favorite.setCreateTime(LocalDateTime.now());
        favorite.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        favorite.setUserId(userId);
        favoriteMapper.add(favorite);
        Integer NOC = favoriteMapper.findNOVbyId(favorite.getVideoId());
        Integer newNOC = NOC + 1;
//        System.out.println(newNOC);
        favoriteMapper.setNOC(newNOC,favorite.getVideoId());
    }

    @Override
    public Favorite findByVideoId(Favorite favorite) {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        favorite.setUserId(userId);
        Favorite fe = favoriteMapper.findByVideoId(favorite);
        return fe;
    }

    @Override
    public List<favoriteResult> fDetail() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<favoriteResult> r=favoriteMapper.fDetail(userId);
        return r;
    }

    @Override
    public void delete(Integer userId,String videoId) {
        favoriteMapper.deleteById(userId,videoId);
    }
}
