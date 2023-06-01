package org.palladiosimulator.dataflow.diagramgenerator.model;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.AbstractActionSequenceElement;

public class DataFlowNode {
	private DataFlowElement element;
	private AbstractActionSequenceElement originalElement;
	private List<DataFlowNode> parents;
	private List<DataFlowNode> children;

	public DataFlowNode(AbstractActionSequenceElement originalElement, DataFlowElement element) {
		this.element = element;
		this.originalElement = originalElement;
		this.parents = new ArrayList<DataFlowNode>();
		this.children = new ArrayList<DataFlowNode>();
	}

	public DataFlowElement getElement() {
		return element;
	}

	public AbstractActionSequenceElement getOritignalElement() {
		return this.originalElement;
	}

	public List<DataFlowNode> getParents() {
		return this.parents;
	}

	public boolean hasParent(DataFlowNode parent) {
		if (this.parents.contains(parent))
			return true;
		return false;
	}

	public void addParent(DataFlowNode parent) {
		if (!this.parents.contains(parent))
			this.parents.add(parent);
	}

	public void removeParent(DataFlowNode parent) {
		this.parents.remove(parent);
	}

	public List<DataFlowNode> getChildren() {
		return this.children;
	}

	public boolean hasChild(DataFlowNode child) {
		if (this.children.contains(child))
			return true;
		return false;
	}

	public void addChild(DataFlowNode child) {
		if (!this.children.contains(child))
			this.children.add(child);
	}

	public void removeChild(DataFlowNode child) {
		this.children.remove(child);
	}

	public void accept(DataFlowNodeVisitor visitor) {
		visitor.visit(this);
	}
}
