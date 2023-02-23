package com.yf.utils;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 描述:数据库注解主库
 *
 * @author Luochuan </br>
 * 创建时间: 2020年10月19日
 * 版权及版本: Copyright(C)2020 一站网版权所有  V1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface MasterRepository {

}
