package com.yf.mapper;

import com.yf.entity.UserEntity;
import com.yf.utils.MasterRepository;

@MasterRepository
public interface UserMapper {


    int insert(UserEntity userEntity);
}
