package org.palladiosimulator.dataflow.diagramgenerator.model;

import java.util.Objects;

public class DataFlowElement {
	private String id;
	private Boolean isCalling;
	private String name;

	public DataFlowElement(String id, Boolean isCalling, String name) {
		this.id = id;
		this.isCalling = isCalling;
		this.name = name;
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
}
