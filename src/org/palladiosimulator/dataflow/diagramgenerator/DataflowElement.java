package org.palladiosimulator.dataflow.diagramgenerator;

import java.util.ArrayList;
import java.util.List;

public class DataflowElement {
	private String id;
	private int numId;
	private String name;
	private Boolean isCalling;
	private String parameter;
	private int occurance;
	private DataflowElement parent;
	private String className;

	public DataflowElement(String id, int numId, String name, Boolean isCalling, DataflowElement parent,
			String parameter, int occurance, String className) {
		super();
		this.id = id;
		this.numId = numId;
		this.name = name;
		this.isCalling = isCalling;
		this.parameter = parameter;
		this.occurance = occurance;
		this.parent = parent;
		this.className = className;
	}

	public String getId() {
		return id;
	}

	public int getNumId() {
		return this.numId;
	}

	public String getName() {
		return name;
	}

	public Boolean getIsCalling() {
		return isCalling;
	}

	public String getParameter() {
		return parameter;
	}

	public int getOccurance() {
		return occurance;
	}

	public DataflowElement getParent() {
		return parent;
	}
	
	public void setParent(DataflowElement parent) {
		this.parent = parent;
	}

	public String getClassName() {
		return className;
	}
}
