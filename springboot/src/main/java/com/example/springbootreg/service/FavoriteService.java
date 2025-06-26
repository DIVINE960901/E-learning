package com.example.springbootreg.service;

import com.example.springbootreg.pojo.Favorite;
import com.example.springbootreg.pojo.favoriteResult;

import java.util.List;

public interface FavoriteService {
    void add(Favorite favorite);

    Favorite findByVideoId(Favorite favorite);

    List<favoriteResult> fDetail();


    void delete(Integer userId,String videoId);
}
