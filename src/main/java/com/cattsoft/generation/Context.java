package com.cattsoft.generation;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Context {
	private String id;
	private String defaultModelType;
	private String introspectedColumnImpl;
	private String targetRuntime;
	private JdbcConnection jdbcConnection;
	private List<Table> list;

	@XmlAttribute(required = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public String getDefaultModelType() {
		return defaultModelType;
	}

	public void setDefaultModelType(String defaultModelType) {
		this.defaultModelType = defaultModelType;
	}

	@XmlAttribute
	public String getIntrospectedColumnImpl() {
		return introspectedColumnImpl;
	}

	public void setIntrospectedColumnImpl(String introspectedColumnImpl) {
		this.introspectedColumnImpl = introspectedColumnImpl;
	}

	@XmlAttribute
	public String getTargetRuntime() {
		return targetRuntime;
	}

	public void setTargetRuntime(String targetRuntime) {
		this.targetRuntime = targetRuntime;
	}

	@XmlElement(name = "jdbcConnection")
	public JdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(JdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}

	@XmlElement(name = "table")
	public List<Table> getList() {
		return list;
	}

	public void setList(List<Table> list) {
		this.list = list;
	}

}
