package com.praver.springboot.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * Mybatis-Plus代码生成器（新）
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generator();
    }

    private static void generator() {
        String url = "jdbc:mysql://localhost:3306/permissions?serverTimezone=GMT%2b8";
        String username = "root";
        String password = "root";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("praver") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\D\\javaWB\\code\\Permissions\\springboot\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.praver.springboot") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    "C:\\D\\javaWB\\code\\Permissions\\springboot\\src\\main\\resources\\mapper\\"));// 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
//                    配置实体类的lombok支持
                    builder.entityBuilder().enableLombok();
//                    给生成的mapper上加上@Mapper
                    builder.mapperBuilder().enableMapperAnnotation().build();

                    builder.controllerBuilder().enableHyphenStyle()//开启驼峰转连字符
                                    .enableRestStyle();//开启生成@RequestController 控制器
                    builder.addInclude("t_comment") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_", "sys_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
