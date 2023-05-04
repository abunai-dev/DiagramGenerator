package org.palladiosimulator.dataflow.diagramgenerator.model;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.AbstractActionSequenceElement;

public class DataFlowNode {
	private String id;
	private int idNumber;
	private String name;
	private AbstractActionSequenceElement element;
	private DataFlowNode parent;
	private List<DataFlowNode> children;

	public DataFlowNode(String id, int idNumber, String name, DataFlowNode parent, AbstractActionSequenceElement element) {
		// remove underscore from id
		this.id = id;
		this.idNumber = idNumber;
		this.name = name;
		this.element = element;
		this.parent = parent;
		this.children = new ArrayList<DataFlowNode>();
	}

	public String getId() {
		return id;
	}
	
	public int getIdNumber() {
		return this.idNumber;
	}
	
	public String getName() {
		return this.name;
	}

	public DataFlowNode getParent() {
		return this.parent;
	}

	public AbstractActionSequenceElement getElement() {
		return element;
	}

	public List<DataFlowNode> getChildren() {
		return this.children;
	}

	public void addChild(DataFlowNode child) {
		if (!this.children.contains(child))
			this.children.add(child);
	}
}
