package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {

    User getByOpenId(String openid);

    void insert(User user);

    User getById(Long userId);

    Integer sumOfNewUser(Map map);

    Integer sumOfAllUser(Map map);

}
