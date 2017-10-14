package com.cattsoft.generation;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "generatorConfiguration")
public class GeneratorConfiguration {
	private List<Context> list;

	@XmlElement(name = "context")
	public List<Context> getList() {
		return list;
	}

	public void setList(List<Context> list) {
		this.list = list;
	}
}
