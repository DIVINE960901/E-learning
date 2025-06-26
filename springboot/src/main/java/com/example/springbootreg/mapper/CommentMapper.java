package com.example.springbootreg.mapper;

import com.example.springbootreg.pojo.Comment;
import com.example.springbootreg.pojo.commentR;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(user_id,video_id,content,create_time)"+
            "values (#{userId},#{videoId},#{content},#{createTime})")
    void add(Comment comment);

    @Delete("delete from comment where id=#{id}")
    void deleteById(Integer id);

    @Select("select c.id, c.content,c.create_time,u.nickname, u.user_pic from comment c join user u on c.user_id=u.id where c.video_id = #{videoId} ORDER BY c.create_time ASC")
    List<commentR> findByVideoId(String videoId);


}
