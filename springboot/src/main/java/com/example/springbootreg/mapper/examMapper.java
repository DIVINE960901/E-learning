package com.example.springbootreg.mapper;

import com.example.springbootreg.pojo.exam;
import com.example.springbootreg.pojo.topic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface examMapper {

    @Insert("insert into exam(title,user_id,nickname,single_number,`describe`,create_time,update_time)"+
            "values (#{title},#{userId},#{nickname},#{singleNumber},#{describe},#{createTime},#{updateTime})")
    void add(exam exam);

    @Select("select * from exam where user_id=#{userId}")
    List<exam> findByUserId(Integer userId);

    @Insert("insert into topic(topic_title,option_A,option_B,option_C,option_D,answer,exam_id)"+
            "values (#{topicTitle},#{optionA},#{optionB},#{optionC},#{optionD},#{answer},#{examId})")
    void addTopic(topic topic);

    @Select("select * from topic where exam_id=#{examId}")
    List<topic> findByExamId(Integer examId);

    @Delete("delete from exam where id=#{id}")
    void deleteById(Integer id);


    @Select("select * from exam")
    List<exam> findAll();


}
