package com.example.demomybatisredis;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class MyCommentGenerator extends DefaultCommentGenerator {
    //添加注释总开关，generator.xml中设置为true！！！
    private boolean addRemarkComments = false;
    //是否需要添加swaggerui注释
    private boolean addSwaggerUi = false;
    //是否需要添加顶部作者姓名和创建时间
    private boolean addAuthorAndDate = true;
    //是否需要Lombok添加@Data注释
    private boolean addLombok = true;
    private Properties properties;
    private static final String LOMBOK_DATA = "lombok.Data";
    private static final String EXAMPLE_SUFFIX = "Example";
    private static final String API_MODEL_PROPERTY_FULL_CLASS_NAME = "io.swagger.annotations.ApiModelProperty";

    public MyCommentGenerator() {
        properties = new Properties();
    }

    /**
     * 设置用户配置的参数
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        super.addConfigurationProperties(properties);
        this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    }

    /**
     * 给字段添加注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        //根据参数和备注信息判断是否添加备注信息
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
//            addFieldJavaDoc(field, remarks);
            //数据库中特殊字符需要转义
            if (remarks.contains("\"")) {
                remarks = remarks.replace("\"", "'");
            }
            //数据库字段注释
            field.addJavaDocLine("/**");
            field.addJavaDocLine("*" + remarks + "");
            field.addJavaDocLine("*/");

            //给model的字段添加swagger注解
            if (addSwaggerUi) {
                field.addJavaDocLine("@ApiModelProperty(value = \"" + remarks + "\")");
            }

        }
    }


    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        if (addSwaggerUi) {
            //只在model中添加swagger注解类的导入
            if (!compilationUnit.isJavaInterface() && !compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
                compilationUnit.addImportedType(new FullyQualifiedJavaType(API_MODEL_PROPERTY_FULL_CLASS_NAME));
            }
        }
        if (addLombok) {
            if (!compilationUnit.isJavaInterface() && !compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
                compilationUnit.addImportedType(new FullyQualifiedJavaType(LOMBOK_DATA));
            }
        }


    }

    /**
     * 在生成的model上添加注释
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass,
                                     IntrospectedTable introspectedTable) {
        //addRemarkComments为true则执行
        if (!addAuthorAndDate) {
            return;
        }
        String author = properties.getProperty("author");
        String dateFormat = properties.getProperty("dateFormat", "yyyy-MM-dd");
        SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);

        // 获取表注释
        String remarks = introspectedTable.getRemarks();

        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + remarks);
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * @author " + author);
        topLevelClass.addJavaDocLine(" * @date " + dateFormatter.format(new Date()));
        topLevelClass.addJavaDocLine(" */");
        if (addLombok) {
            topLevelClass.addJavaDocLine("@Data");

        }
    }

}
