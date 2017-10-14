package org.mybatis.generator.plugins;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class PagingByOraclePlugin extends PluginAdapter {

	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		addSelectAllMethod(interfaze, introspectedTable);
		addSelectAllCountMethod(interfaze, introspectedTable);
		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}

	private void addSelectAllMethod(Interface interfaze, IntrospectedTable introspectedTable) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		importedTypes.add(new FullyQualifiedJavaType("java.util.List"));
		importedTypes.add(new FullyQualifiedJavaType("com.cattsoft.baseplatform.core.entity.PagingQueryBean"));
		String querybean = introspectedTable.getRules().calculateAllFieldsClass() + "";
		String prefix = querybean.substring(0, querybean.lastIndexOf("."));
		String suffix = querybean.substring(querybean.lastIndexOf("."),querybean.length());
		importedTypes.add(new FullyQualifiedJavaType(prefix + ".querybean" + suffix + "QB"));
		interfaze.addImportedTypes(importedTypes);

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);

		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("List<" + introspectedTable.getRules().calculateAllFieldsClass() + ">");
		method.setReturnType(returnType);

		method.setName("selectAll");

		FullyQualifiedJavaType type = new FullyQualifiedJavaType("com.cattsoft.baseplatform.core.entity.PagingQueryBean<" + introspectedTable.getRules().calculateAllFieldsClass() + "QB>");
		Parameter parameter = new Parameter(type, "pagingQueryBean");
		method.addParameter(parameter);

		// addMapperAnnotations(interfaze, method);

		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		interfaze.addMethod(method);
	}

	private void addSelectAllCountMethod(Interface interfaze, IntrospectedTable introspectedTable) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		importedTypes.add(new FullyQualifiedJavaType("com.cattsoft.baseplatform.core.entity.PagingQueryBean"));
		String querybean = introspectedTable.getRules().calculateAllFieldsClass() + "";
		String prefix = querybean.substring(0, querybean.lastIndexOf("."));
		String suffix = querybean.substring(querybean.lastIndexOf("."),querybean.length());
		importedTypes.add(new FullyQualifiedJavaType(prefix + ".querybean" + suffix + "QB"));
		interfaze.addImportedTypes(importedTypes);

		Method method = new Method();
		method.setVisibility(JavaVisibility.PUBLIC);

		FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("int");
		method.setReturnType(returnType);

		method.setName("selectAllCount");

		FullyQualifiedJavaType type = new FullyQualifiedJavaType("com.cattsoft.baseplatform.core.entity.PagingQueryBean<" + introspectedTable.getRules().calculateAllFieldsClass() + "QB>");
		Parameter parameter = new Parameter(type, "pagingQueryBean");
		method.addParameter(parameter);

		// addMapperAnnotations(interfaze, method);

		context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

		interfaze.addMethod(method);
	}

	@Override
	public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
		addWhereConditionElement(document, introspectedTable);
		addSelectAllElement(document, introspectedTable);
		addSelectAllCountElement(document, introspectedTable);
		return super.sqlMapDocumentGenerated(document, introspectedTable);
	}

	private XmlElement getBaseColumnListElement(IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("include"); //$NON-NLS-1$
		answer.addAttribute(new Attribute("refid", //$NON-NLS-1$
				introspectedTable.getBaseColumnListId()));
		return answer;
	}

	private XmlElement getBlobColumnListElement(IntrospectedTable introspectedTable) {
		XmlElement answer = new XmlElement("include"); //$NON-NLS-1$
		answer.addAttribute(new Attribute("refid", //$NON-NLS-1$
				introspectedTable.getBlobColumnListId()));
		return answer;
	}

	private void addWhereConditionElement(Document document, IntrospectedTable introspectedTable) {
		XmlElement xmlElement = document.getRootElement();
		XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$
		context.getCommentGenerator().addComment(answer);
		answer.addAttribute(new Attribute("id", "condition")); //$NON-NLS-1$
		XmlElement where = new XmlElement("where"); //$NON-NLS-1$
		StringBuilder sb = new StringBuilder();
		for (IntrospectedColumn introspectedColumn : introspectedTable.getAllColumns()) {
			XmlElement whereNotNullElement = new XmlElement("if"); //$NON-NLS-1$
			sb.setLength(0);
			sb.append("queryBean." + introspectedColumn.getJavaProperty());
			sb.append(" != null and "); //$NON-NLS-1$
			sb.append("queryBean." + introspectedColumn.getJavaProperty());
			sb.append(" != ''"); //$NON-NLS-1$
			whereNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$

			sb.setLength(0);
			sb.append("and ");
			sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
			sb.append(" = ");
			sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "queryBean."));
			whereNotNullElement.addElement(new TextElement(sb.toString()));
			where.addElement(whereNotNullElement);
		}
		answer.addElement(where);
		xmlElement.addElement(answer);
	}

	private void addSelectAllElement(Document document, IntrospectedTable introspectedTable) {
		XmlElement xmlElement = document.getRootElement();

		XmlElement answer = new XmlElement("select");

		answer.addAttribute(new Attribute("id", "selectAll"));

		if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
			answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
					introspectedTable.getResultMapWithBLOBsId()));
		} else {
			answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
					introspectedTable.getBaseResultMapId()));
		}

		answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
				"PagingQueryBean"));

		context.getCommentGenerator().addComment(answer);

		XmlElement prefixElement = new XmlElement("include");
		prefixElement.addAttribute(new Attribute("refid", "com.cattsoft.skill.common.persistence.CommonMapper.pageHeader"));
		answer.addElement(prefixElement);

		StringBuilder sb = new StringBuilder();

		sb.append("select "); //$NON-NLS-1$

		if (stringHasValue(introspectedTable.getSelectByPrimaryKeyQueryId())) {
			sb.append('\'');
			sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
			sb.append("' as QUERYID,"); //$NON-NLS-1$
		}
		answer.addElement(new TextElement(sb.toString()));
		answer.addElement(getBaseColumnListElement(introspectedTable));
		if (introspectedTable.hasBLOBColumns()) {
			answer.addElement(new TextElement(",")); //$NON-NLS-1$
			answer.addElement(getBlobColumnListElement(introspectedTable));
		}

		sb.setLength(0);
		sb.append("from "); //$NON-NLS-1$
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		answer.addElement(new TextElement(sb.toString()));

		XmlElement includeElement = new XmlElement("include");
		includeElement.addAttribute(new Attribute("refid", "condition"));
		answer.addElement(includeElement);

		XmlElement suffixElement = new XmlElement("include");
		suffixElement.addAttribute(new Attribute("refid", "com.cattsoft.skill.common.persistence.CommonMapper.pageFoot"));
		answer.addElement(suffixElement);

		xmlElement.addElement(answer);
	}

	private void addSelectAllCountElement(Document document, IntrospectedTable introspectedTable) {
		XmlElement xmlElement = document.getRootElement();

		XmlElement answer = new XmlElement("select");

		answer.addAttribute(new Attribute("id", "selectAllCount"));

		answer.addAttribute(new Attribute("resultType",//$NON-NLS-1$ 
				"java.lang.Integer"));

		context.getCommentGenerator().addComment(answer);

		XmlElement prefixElement = new XmlElement("include");
		prefixElement.addAttribute(new Attribute("refid", "com.cattsoft.skill.common.persistence.CommonMapper.countHeader"));
		answer.addElement(prefixElement);

		StringBuilder sb = new StringBuilder();

		sb.append("select "); //$NON-NLS-1$

		if (stringHasValue(introspectedTable.getSelectByPrimaryKeyQueryId())) {
			sb.append('\'');
			sb.append(introspectedTable.getSelectByPrimaryKeyQueryId());
			sb.append("' as QUERYID,"); //$NON-NLS-1$
		}
		answer.addElement(new TextElement(sb.toString()));
		answer.addElement(getBaseColumnListElement(introspectedTable));
		if (introspectedTable.hasBLOBColumns()) {
			answer.addElement(new TextElement(",")); //$NON-NLS-1$
			answer.addElement(getBlobColumnListElement(introspectedTable));
		}

		sb.setLength(0);
		sb.append("from "); //$NON-NLS-1$
		sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
		answer.addElement(new TextElement(sb.toString()));

		XmlElement includeElement = new XmlElement("include");
		includeElement.addAttribute(new Attribute("refid", "condition"));
		answer.addElement(includeElement);

		XmlElement suffixElement = new XmlElement("include");
		suffixElement.addAttribute(new Attribute("refid", "com.cattsoft.skill.common.persistence.CommonMapper.countFoot"));
		answer.addElement(suffixElement);

		xmlElement.addElement(answer);
	}
}
