package com.example.springbootreg.mapper;

import com.example.springbootreg.pojo.Favorite;
import com.example.springbootreg.pojo.favoriteResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    @Select("select * from favorite where video_id=#{videoId} and user_id = #{userId}")
    Favorite findByVideoId(Favorite favorite);

    @Insert("insert into favorite(user_id,video_id,create_time,update_time)"
            +"values (#{userId},#{videoId},#{createTime},#{updateTime})"
    )
    void add(Favorite favorite);

    @Select("select *\n" +
            " from (select c.id, c.title, c.cover_img, c.create_user, f.video_id, f.user_id\n" +
            "       from course c,\n" +
            "            favorite f\n" +
            "       where c.id = f.video_id) cf where cf.user_id=#{userId}")
    List<favoriteResult> fDetail(Integer userId);

    @Delete("delete from favorite where user_id = #{userId} and video_id = #{videoId}")
    void deleteById(Integer userId, String videoId);
    @Update("update course set Number_of_Collections = #{noc} where id=#{videoId}")
    void setNOC(Integer noc,String videoId);

    @Select("select Number_of_Collections from course where id=#{videoId}")
    Integer findNOVbyId(String videoId);
}
