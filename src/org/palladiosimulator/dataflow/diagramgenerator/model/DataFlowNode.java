package org.palladiosimulator.dataflow.diagramgenerator.model;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.AbstractActionSequenceElement;

public class DataFlowNode {
	private DataFlowElement element;
	private AbstractActionSequenceElement<?> originalElement;
	private List<DataFlowNode> parents;
	private List<DataFlowNode> children;
	private List<DataFlowLiteral> literals;
	private List<DataFlowElementVariable> variables;

	public DataFlowNode(AbstractActionSequenceElement<?> originalElement, DataFlowElement element) {
		this.element = element;
		this.originalElement = originalElement;
		this.parents = new ArrayList<DataFlowNode>();
		this.children = new ArrayList<DataFlowNode>();
		this.literals = new ArrayList<DataFlowLiteral>();
		this.variables = new ArrayList<DataFlowElementVariable>();
	}

	public DataFlowElement getElement() {
		return element;
	}

	public AbstractActionSequenceElement<?> getOritignalElement() {
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

	public List<DataFlowElementVariable> getVariables() {
		return this.variables;
	}

	public void setVariables(List<DataFlowElementVariable> variables) {
		this.variables = variables;
	}

	public void addVariable(DataFlowElementVariable variable) {
		boolean exists = false;
		for (DataFlowElementVariable comp : this.variables) {
			if (comp.getName().equals(variable.getName()))
				exists = true;
		}
		if (!exists) {
			this.variables.add(variable);
		}
	}

	public void removeVariable(DataFlowElementVariable variable) {
		this.variables.remove(variable);
	}

	public boolean hasVariable(DataFlowElementVariable variable) {
		boolean exists = false;
		for (DataFlowLiteral comp : this.literals) {
			if (comp.getLiteralID().equals(variable.getName()))
				exists = true;
		}
		return exists;
	}

	public List<DataFlowLiteral> getLiterals() {
		return this.literals;
	}

	public void setLiterals(List<DataFlowLiteral> literals) {
		this.literals = literals;
	}

	public void addLiteral(DataFlowLiteral literal) {
		boolean exists = false;
		for (DataFlowLiteral comp : this.literals) {
			if (comp.getLiteralID().equals(literal.getLiteralID()) && comp.getTypeID().equals(literal.getTypeID()))
				exists = true;
		}
		if (!exists) {
			this.literals.add(literal);
		}
	}

	public void removeLiteral(DataFlowLiteral literal) {
		this.literals.remove(literal);
	}

	public boolean hasLiteral(DataFlowLiteral literal) {
		boolean exists = false;
		for (DataFlowLiteral comp : this.literals) {
			if (comp.getLiteralID().equals(literal.getLiteralID()) && comp.getTypeID().equals(literal.getTypeID()))
				exists = true;
		}
		return exists;
	}
}
