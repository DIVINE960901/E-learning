package com.example.springbootreg.mapper;

import com.example.springbootreg.pojo.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface adminMapper {

    @Select("select * from user where username=#{username} and identity=#{identity}")
    User findByUserName(String username,String identity);

    @Select("select * from course")
    List<Course> findAllCourse();

    @Select("select c.id,c.title,c.content_Url,c.state,c.cover_img,c.create_time,u.nickname " +
            "from course c join user u on c.create_user=u.id " +
            "where state=#{state}")
    List<courseAdmin> findByState(String state);

    @Select("select c.id, c.content,c.create_time,o.title,u.nickname,o.title" +
            " from comment c join user u on c.user_id=u.id join course o on c.video_id=o.id " +
            "ORDER BY c.create_time ASC")
    List<commentAdmin> findAllComment();

    @Update("update course set state=#{state},update_time=#{updateTime} where id=#{id}")
    void updataState(Course course);


    @Select("select * from user")
    List<User> findAllUser();

    @Update("update user set nickname=#{nickname},username=#{username},identity=#{identity},update_time=#{updateTime} where id=#{id}")
    void updataUser(User user);

    @Delete("delete from user where id=#{id}")
    void deleteUser(Integer id);

    @Update("update carousel set id=#{carousel.id}, nickname=#{carousel.nickname},title=#{carousel.title},cover_img=#{carousel.coverImg} where carouse_id=#{Cid}")
    void updataCarousl(Carousel carousel,Integer Cid);

    @Select("select * from carousel")
    List<Carousel> findAllCarousel();

    @Update("update recommend set video_id=#{recommend.id}, nickname=#{recommend.nickname},title=#{recommend.title},cover_img=#{recommend.coverImg} where id=#{i}")
    void updataRecommend(courseAdmin recommend, int i);

    @Select("select * from recommend")
    List<Recommend> getRecommend();

    @Select("select identity from user where id=#{id}")
    String findById(Integer id);

    @Select("select * from exam")
    List<exam> findAllExam();
}
