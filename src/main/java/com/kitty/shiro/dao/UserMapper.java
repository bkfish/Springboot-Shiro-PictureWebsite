package com.kitty.shiro.dao;

import com.kitty.shiro.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where name = #{name}")
    public User findByName(String name);

    @Select("select * from user where id = #{id}")
    public User findById(Integer id);
}

