package org.palladiosimulator.dataflow.diagramgenerator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class DataFlowElement {
	private String id;
	private Boolean isCalling;
	private String name;
	private List<String> parameters;
	private boolean isControlFlow;

	protected DataFlowElement(String id, Boolean isCalling, String name) {
		this.id = id;
		this.isCalling = isCalling;
		this.name = name;
		this.parameters = new ArrayList<>();
		this.isControlFlow = false;
	}

	public abstract void accept(DataFlowElementVisitor visitor);

	public List<String> getParameters() {
		return parameters;
	}

	public void addParameter(String parameter) {
		this.parameters.add(parameter);
	}

	public void addParameters(List<String> parameters) {
		this.parameters.addAll(parameters);
	}

	public String getId() {
		return id;
	}

	public Boolean getIsCalling() {
		return isCalling;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isCalling);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataFlowElement other = (DataFlowElement) obj;
		return Objects.equals(id, other.id) && Objects.equals(isCalling, other.isCalling);
	}

	public boolean isControlFlow() {
		return isControlFlow;
	}

	public void setControlFlow(boolean isControlFlow) {
		this.isControlFlow = isControlFlow;
	}
}
