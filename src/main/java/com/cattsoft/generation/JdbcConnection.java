package com.cattsoft.generation;

import javax.xml.bind.annotation.XmlAttribute;

public class JdbcConnection {
	private String driverClass;
	private String connectionURL;
	private String userId;
	private String password;

	@XmlAttribute
	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	@XmlAttribute
	public String getConnectionURL() {
		return connectionURL;
	}

	public void setConnectionURL(String connectionURL) {
		this.connectionURL = connectionURL;
	}

	@XmlAttribute
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@XmlAttribute
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
