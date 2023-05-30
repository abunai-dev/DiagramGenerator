package org.palladiosimulator.dataflow.diagramgenerator.model;

import java.util.ArrayList;
import java.util.List;

public class ProcessDataFlowElement extends DataFlowElement {
	private List<String> parameters;

	public ProcessDataFlowElement(String id, Boolean isCalling, String name) {
		super(id, isCalling, name);

		this.parameters = new ArrayList<String>();
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void addParameter(String parameter) {
		this.parameters.add(parameter);
	}
	
	public void addParameters(List<String> parameters) {
		this.parameters.addAll(parameters);
	}
}
