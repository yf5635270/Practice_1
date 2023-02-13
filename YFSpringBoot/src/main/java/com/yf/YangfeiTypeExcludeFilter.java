package com.yf;

import com.yf.service.MemberService;
import com.yf.service.OrderService;
import com.yf.service.UserService;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;

/**
 *  排除过滤
 *  需要通过加载继承ApplicationContextInitializer的类进行注册添才能生效生效
 */
public class YangfeiTypeExcludeFilter extends TypeExcludeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        System.out.println("进入YangfeiTypeExcludeFilter");

        //判断是否有符合的条件类型的类，排除过滤掉
        if(metadataReader.getClassMetadata().getClassName().equals(MemberService.class.getName())){
            return true;
        }

        return false;
    }
}
