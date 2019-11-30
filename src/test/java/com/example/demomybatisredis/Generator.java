package com.example.demomybatisredis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class  Generator {
    public static void main(String[] args) throws Exception {
        //打印警告信息
        List<String> warnings = new ArrayList<String>();
        //是否覆盖原代码
        boolean overwrite = true;
        //读取配置文件，generatorConfig.xml放在resources目录下
        InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        //创建mbg
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        //执行代码
        myBatisGenerator.generate(null);
        //打印警告信息
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}