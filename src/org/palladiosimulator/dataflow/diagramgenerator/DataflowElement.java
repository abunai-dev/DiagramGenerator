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
	private List<DataflowElement> parents;
	private String className;

	public DataflowElement(String id, int numId, String name, Boolean isCalling, String parameter, int occurance,
			String className) {
		super();
		this.id = id;
		this.numId = numId;
		this.name = name;
		this.isCalling = isCalling;
		this.parameter = parameter;
		this.occurance = occurance;
		this.parents = new ArrayList<DataflowElement>();
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
	
	public void addParent(DataflowElement parent) {
		this.parents.add(parent);
	}
	
	public void removeParent(DataflowElement parent) {
		this.parents.remove(parent);
	}

	public List<DataflowElement> getParents() {
		return parents;
	}

	public void setParents(List<DataflowElement> parents) {
		this.parents = parents;
	}

	public String getClassName() {
		return className;
	}
}
