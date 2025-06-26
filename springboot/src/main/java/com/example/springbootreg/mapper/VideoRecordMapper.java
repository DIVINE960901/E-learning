package com.example.springbootreg.mapper;

import com.example.springbootreg.pojo.historyResult;
import com.example.springbootreg.pojo.videoRecord;
import org.apache.ibatis.annotations.*;


import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface VideoRecordMapper {
    @Insert("insert into videorecord(user_id,video_id,create_time,update_time)"+
    "values (#{userId},#{videoId},#{createTime},#{updateTime})")
    void addvideoRecord(videoRecord videoRecord);

    @Select("select *\n" +
            " from (select c.id, c.title, c.cover_img, c.create_user, h.video_id, h.user_id,h.create_time,h.update_time\n" +
            "       from course c,\n" +
            "            videorecord h\n" +
            "       where c.id = h.video_id) cf where cf.user_id=#{userId} ORDER BY update_time DESC")
    List<historyResult> findbyUserId(Integer userId);

    @Select("select * from videorecord where video_id=#{videoId}")
    String findByVideoId(String videoId);


    @Delete("delete from videorecord where user_id=#{userId} and video_id=#{videoId}")
    void deleteById(Integer userId, String videoId);

    @Update("update videorecord set update_time=#{now} where video_id=#{videoId}")
    void updataVideoRecord(String videoId, LocalDateTime now);
}
