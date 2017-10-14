/*
 *  Copyright 2008 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.mybatis.generator.internal;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;

/**
 * @author Jeff Butler
 * 
 */
public class DefaultCommentGenerator implements CommentGenerator {

    private Properties properties;
    private boolean suppressDate;
    private boolean suppressAllComments;

    public DefaultCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
    }

    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments by default
        return;
    }

    /**
     * Adds a suitable comment to warn users that the element was generated, and
     * when it was generated.
     */
    public void addComment(XmlElement xmlElement) {
//        if (suppressAllComments) {
            return;
//        }
//
//        xmlElement.addElement(new TextElement("<!--")); //$NON-NLS-1$
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("  WARNING - "); //$NON-NLS-1$
//        sb.append(MergeConstants.NEW_ELEMENT_TAG);
//        xmlElement.addElement(new TextElement(sb.toString()));
//        xmlElement
//                .addElement(new TextElement(
//                        "  This element is automatically generated by MyBatis Generator, do not modify.")); //$NON-NLS-1$
//
//        String s = getDateString();
//        if (s != null) {
//            sb.setLength(0);
//            sb.append("  This element was generated on "); //$NON-NLS-1$
//            sb.append(s);
//            sb.append('.');
//            xmlElement.addElement(new TextElement(sb.toString()));
//        }
//
//        xmlElement.addElement(new TextElement("-->")); //$NON-NLS-1$
    }

    public void addRootComment(XmlElement rootElement) {
        // add no document level comments by default
        return;
    }

    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);

        suppressDate = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        
        suppressAllComments = isTrue(properties
                .getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
    }

    /**
     * This method adds the custom javadoc tag for. You may do nothing if you do
     * not wish to include the Javadoc tag - however, if you do not include the
     * Javadoc tag then the Java merge capability of the eclipse plugin will
     * break.
     * 
     * @param javaElement
     *            the java element
     */
    protected void addJavadocTag(JavaElement javaElement,
            boolean markAsDoNotDelete) {
//        javaElement.addJavaDocLine(" *"); //$NON-NLS-1$
//        StringBuilder sb = new StringBuilder();
//        sb.append(" * "); //$NON-NLS-1$
//        sb.append(MergeConstants.NEW_ELEMENT_TAG);
//        if (markAsDoNotDelete) {
//            sb.append(" do_not_delete_during_merge"); //$NON-NLS-1$
//        }
//        String s = getDateString();
//        if (s != null) {
//            sb.append(' ');
//            sb.append(s);
//        }
//        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * This method returns a formated date string to include in the Javadoc tag
     * and XML comments. You may return null if you do not want the date in
     * these documentation elements.
     * 
     * @return a string representing the current timestamp, or null
     */
    protected String getDateString() {
        if (suppressDate) {
            return null;
        } else {
            return new Date().toString();
        }
    }

    public void addClassComment(InnerClass innerClass,
            IntrospectedTable introspectedTable) {
//        if (suppressAllComments) {
//            return;
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
//        innerClass
//                .addJavaDocLine(" * This class was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This class corresponds to the database table "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        innerClass.addJavaDocLine(sb.toString());
//
//        addJavadocTag(innerClass, false);
//
//        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$

        innerClass.addJavaDocLine("/**");
        innerClass.addJavaDocLine("*");
        innerClass.addJavaDocLine("* Description: <br>");
        innerClass.addJavaDocLine("* Copyright:DATANG SOFTWARE CO.LTD<br>");
        innerClass.addJavaDocLine("*");
        innerClass.addJavaDocLine("* @author whoareyou");
        innerClass.addJavaDocLine("*");
        innerClass.addJavaDocLine("*/");
    }

    public void addEnumComment(InnerEnum innerEnum,
            IntrospectedTable introspectedTable) {
//        if (suppressAllComments) {
            return;
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        innerEnum.addJavaDocLine("/**"); //$NON-NLS-1$
//        innerEnum
//                .addJavaDocLine(" * This enum was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This enum corresponds to the database table "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        innerEnum.addJavaDocLine(sb.toString());
//
//        addJavadocTag(innerEnum, false);
//
//        innerEnum.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addFieldComment(Field field,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        field.addJavaDocLine("/**"); //$NON-NLS-1$
//        field.addJavaDocLine(" * This field was generated by MyBatis Generator."); //$NON-NLS-1$

//        sb.append(" * This field corresponds to the database column "); //$NON-NLS-1$
        sb.append("* "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append('.');
        sb.append(introspectedColumn.getActualColumnName());
        field.addJavaDocLine(sb.toString());

        addJavadocTag(field, false);

        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
//        if (suppressAllComments) {
            return;
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        field.addJavaDocLine("/**"); //$NON-NLS-1$
//        field
//                .addJavaDocLine(" * This field was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This field corresponds to the database table "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        field.addJavaDocLine(sb.toString());
//
//        addJavadocTag(field, false);
//
//        field.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addGeneralMethodComment(Method method,
            IntrospectedTable introspectedTable) {
//        if (suppressAllComments) {
//            return;
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        method.addJavaDocLine("/**"); //$NON-NLS-1$
//        method
//                .addJavaDocLine(" * This method was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This method corresponds to the database table "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        method.addJavaDocLine(sb.toString());
//
//        addJavadocTag(method, false);
//
//        method.addJavaDocLine(" */"); //$NON-NLS-1$

        StringBuilder sb = new StringBuilder();
        method.addJavaDocLine("/**"); //$NON-NLS-1$
//        method.addJavaDocLine(" * This method was generated by MyBatis Generator."); //$NON-NLS-1$

//        sb.append(" * This method corresponds to the database table "); //$NON-NLS-1$
        sb.append(" * ");
        if (method.isConstructor()) {
            sb.append(" 构造查询条件");
        }
        String method_name = method.getName();
        if ("setOrderByClause".equals(method_name)) {
            sb.append("Description: 设置排序字段<br>");
        } else if ("setDistinct".equals(method_name)) {
            sb.append("Description: 设置过滤重复数据<br>");
        } else if ("getOredCriteria".equals(method_name)) {
            sb.append("Description: 获取当前的查询条件实例<br>");
        } else if ("isDistinct".equals(method_name)) {
            sb.append("Description: 是否过滤重复数据<br>");
        } else if ("getOrderByClause".equals(method_name)) {
            sb.append("Description: 获取排序字段<br>");
        } else if ("createCriteria".equals(method_name)) {
            sb.append("Description: 创建一个查询条件<br>");
        } else if ("createCriteriaInternal".equals(method_name)) {
            sb.append("Description: 内部构建查询条件对象<br>");
        } else if ("clear".equals(method_name)) {
            sb.append("Description: 清除查询条件<br>");
        } else if ("countByExample".equals(method_name)) {
            sb.append("Description: 根据指定的条件获取数据库记录数<br>");
        } else if ("deleteByExample".equals(method_name)) {
            sb.append("Description: 根据指定的条件删除数据库符合条件的记录<br>");
        } else if ("deleteByPrimaryKey".equals(method_name)) {
            sb.append("Description: 根据主键删除数据库的记录<br>");
        } else if ("insert".equals(method_name)) {
            sb.append("Description: 新写入数据库记录<br>");
        } else if ("insertSelective".equals(method_name)) {
            sb.append("Description: 动态字段,写入数据库记录<br>");
        } else if ("selectByExample".equals(method_name)) {
            sb.append("Description: 根据指定的条件查询符合条件的数据库记录<br>");
        } else if ("selectByPrimaryKey".equals(method_name)) {
            sb.append("Description: 根据指定主键获取一条数据库记录<br>");
        } else if ("updateByExampleSelective".equals(method_name)) {
            sb.append("Description: 动态根据指定的条件来更新符合条件的数据库记录<br>");
        } else if ("updateByExample".equals(method_name)) {
            sb.append("Description: 根据指定的条件来更新符合条件的数据库记录<br>");
        } else if ("updateByPrimaryKeySelective".equals(method_name)) {
            sb.append("Description: 动态字段,根据主键来更新符合条件的数据库记录<br>");
        } else if ("updateByPrimaryKey".equals(method_name)) {
            sb.append("Description: 根据主键来更新符合条件的数据库记录<br>");
        } else if ("selectAll".equals(method_name)) {
            sb.append("Description: 根据指定的条件查询符合条件的数据库记录<br>");
        } else if ("selectAllCount".equals(method_name)) {
            sb.append("Description: 根据指定的条件统计符合条件的数据库记录数<br>");
        }
//        sb.append(",");
//        sb.append(introspectedTable.getFullyQualifiedTable());
        method.addJavaDocLine(sb.toString());

        final List<Parameter> parameterList = method.getParameters();
        if (!parameterList.isEmpty()) {
            method.addJavaDocLine(" *");
            if ("or".equals(method_name)) {
                sb.append(" 增加或者的查询条件,用于构建或者查询");
            }
        } else {
            if ("or".equals(method_name)) {
                sb.append(" 创建一个新的或者查询条件");
            }
        }
        String paramterName;
        for (Parameter parameter : parameterList) {
            sb.setLength(0);
            sb.append(" * @param "); //$NON-NLS-1$
            paramterName = parameter.getName();
            sb.append(paramterName);
            if ("orderByClause".equals(paramterName)) {
                sb.append(" 排序字段"); //$NON-NLS-1$
            } else if ("distinct".equals(paramterName)) {
                sb.append(" 是否过滤重复数据");
            } else if ("criteria".equals(paramterName)) {
                sb.append(" 过滤条件实例");
            }
            method.addJavaDocLine(sb.toString());
        }


        //        addJavadocTag(method, false);

        method.addJavaDocLine(" */"); //$NON-NLS-1$

    }

    public void addGetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
//        if (suppressAllComments) {
            return;
//        }

//        StringBuilder sb = new StringBuilder();
//
//        method.addJavaDocLine("/**"); //$NON-NLS-1$
//        method
//                .addJavaDocLine(" * This method was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This method returns the value of the database column "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        sb.append('.');
//        sb.append(introspectedColumn.getActualColumnName());
//        method.addJavaDocLine(sb.toString());
//
//        method.addJavaDocLine(" *"); //$NON-NLS-1$
//
//        sb.setLength(0);
//        sb.append(" * @return the value of "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        sb.append('.');
//        sb.append(introspectedColumn.getActualColumnName());
//        method.addJavaDocLine(sb.toString());
//
//        addJavadocTag(method, false);
//
//        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addSetterComment(Method method,
            IntrospectedTable introspectedTable,
            IntrospectedColumn introspectedColumn) {
//        if (suppressAllComments) {
            return;
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        method.addJavaDocLine("/**"); //$NON-NLS-1$
//        method
//                .addJavaDocLine(" * This method was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This method sets the value of the database column "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        sb.append('.');
//        sb.append(introspectedColumn.getActualColumnName());
//        method.addJavaDocLine(sb.toString());
//
//        method.addJavaDocLine(" *"); //$NON-NLS-1$
//
//        Parameter parm = method.getParameters().get(0);
//        sb.setLength(0);
//        sb.append(" * @param "); //$NON-NLS-1$
//        sb.append(parm.getName());
//        sb.append(" the value for "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        sb.append('.');
//        sb.append(introspectedColumn.getActualColumnName());
//        method.addJavaDocLine(sb.toString());
//
//        addJavadocTag(method, false);
//
//        method.addJavaDocLine(" */"); //$NON-NLS-1$
    }

    public void addClassComment(InnerClass innerClass,
            IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
//        if (suppressAllComments) {
            return;
//        }
//
//        StringBuilder sb = new StringBuilder();
//
//        innerClass.addJavaDocLine("/**"); //$NON-NLS-1$
//        innerClass
//                .addJavaDocLine(" * This class was generated by MyBatis Generator."); //$NON-NLS-1$
//
//        sb.append(" * This class corresponds to the database table "); //$NON-NLS-1$
//        sb.append(introspectedTable.getFullyQualifiedTable());
//        innerClass.addJavaDocLine(sb.toString());
//
//        addJavadocTag(innerClass, markAsDoNotDelete);
//
//        innerClass.addJavaDocLine(" */"); //$NON-NLS-1$
    }
}
