package com.example.springbootreg.mapper;

import com.example.springbootreg.pojo.LogDateCount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface logDataMapper {
    @Insert("insert into log(user_id,exam_id,accuracy,create_time,update_time)"+"values (#{userId},#{examId},#{accuracy},now(),now())")
    void addData(Integer userId, Integer examId, float accuracy);


    @Select("select AVG(accuracy) from log where exam_id=#{examId}")
    float logDataGroupby(Integer examId);

    @Select("SELECT DATE(create_time) AS date_group, COUNT(*) AS total_count FROM log where exam_id=#{examId} GROUP BY DATE(create_time)" +
            "ORDER BY date_group")
    List<LogDateCount> getLogDataGroupedByDate(Integer examId);

}
