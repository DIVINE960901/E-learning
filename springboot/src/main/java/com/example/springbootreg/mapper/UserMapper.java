package com.example.springbootreg.mapper;

import com.example.springbootreg.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //用户查询
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);
    //添加
    @Insert("insert  into user(username,password,create_time,update_time)"+"values (#{username},#{password},now(),now())")
    void add(String username, String password);
    @Update("update user set nickname=#{nickname},update_time=#{updateTime} where id=#{id}")
    void update(User user);
    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);
    @Update("update user set password=#{newPwd},update_time=now() where id=#{id}")
    void updatePwd(String newPwd, Integer id);

}
