package com.yf.generrator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MybatiesPlusGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/leon","root","123456")
                .globalConfig(
                        builder -> {
                            builder.author("yangfei")
                                    .fileOverride()
                                    .outputDir(System.getProperty("user.dir")+"/YFSpringBoot/src/main/java");
                        }
                )
                .packageConfig(
                        builder -> {
                            builder.parent("com.yf")
                                    .pathInfo(Collections.singletonMap(
                                            OutputFile.mapperXml,
                                            System.getProperty("user.dir")+"/YFSpringBoot/src/main/resources/mapper/"));
                        }
                )
                .strategyConfig(
                        builder ->{
                            builder.addInclude("student");
                        }
                )
                .templateEngine(new FreemarkerTemplateEngine()) //使用freemarker引擎模板，默认Velocity引擎模板
                .execute();



    }
}
