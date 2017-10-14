package com.cattsoft.generation;

import javax.xml.bind.annotation.XmlAttribute;

public class Table {
	private String schema;
	private String tableName;

	@XmlAttribute(name = "schema")
	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	@XmlAttribute(name = "tableName")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
