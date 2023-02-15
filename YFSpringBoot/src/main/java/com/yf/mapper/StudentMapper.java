package com.yf.mapper;

import com.yf.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangfei
 * @since 2023-02-15
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
