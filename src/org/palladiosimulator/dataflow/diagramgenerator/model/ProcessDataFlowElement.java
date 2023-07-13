package org.palladiosimulator.dataflow.diagramgenerator.model;

public class ProcessDataFlowElement extends DataFlowElement {

	public ProcessDataFlowElement(String id, Boolean isCalling, String name) {
		super(id, isCalling, name);
	}

	@Override
	public Object accept(DataFlowElementVisitor<?> visitor) {
		return visitor.visit(this);
	}
}
