package com.example.springbootreg.mapper;

import com.example.springbootreg.pojo.Course;
import com.example.springbootreg.pojo.courseAdmin;
import com.example.springbootreg.pojo.courseDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Insert("insert into course(id,title,content_Url,cover_img,state,create_user,create_time,update_time)"+
            "values (#{id},#{title},#{contentUrl},#{coverImg},#{state},#{createUser},#{createTime},#{updateTime})")
    void add(Course course);

    @Select("select * from course where create_user=#{createUser}")
    List<Course> findByCreateId(Integer createUser);

    @Select("select c.id,c.title,c.content_Url,c.state,c.cover_img,c.create_time,u.nickname " +
            "from course c join user u on c.create_user=u.id " +
            "where state=#{state}")
    List<courseAdmin> findByState(String state);

    @Select("select content_Url from course where id=#{id}")
    String findById(String id);

    @Select("SELECT c.id, c.title, c.state, u.nickname ," +
            " c.create_Time, c.Number_of_Collections,c.Number_of_viewers " +
            "FROM course c JOIN user u ON c.create_user = u.id " +
            "WHERE c.create_user = #{id}")
    List<courseDetail> findAssociation(Integer id);

    @Delete("delete from course where id=#{id}")
    void deleteById(String id);

    @Select("select cover_img from course where id=#{id}")
    String findImageById(String id);

    @Select("select c.title, u.nickname " +
            "from course c join user u on c.create_user=u.id " +
            "where c.id = #{id}")
    courseDetail findPlayDataById(String id);

    @Select("select Number_of_viewers from course where id=#{videoId}")
    Integer findNOVbyId(String videoId);

    @Update("update course set Number_of_viewers = #{nov} where id=#{videoId}")
    void setNOV(String videoId, Integer nov);

    @Select("select create_user from course where id=#{videoId}")
    String getVideoCreateId(String videoId);
}
