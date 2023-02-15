package com.yf.service.impl;

import com.yf.entity.Student;
import com.yf.mapper.StudentMapper;
import com.yf.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangfei
 * @since 2023-02-15
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
