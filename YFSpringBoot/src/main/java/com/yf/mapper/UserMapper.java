package com.yf.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("insert into user(name,age,gender) values('yangfei',37,1)")
    void insertOne();


}
