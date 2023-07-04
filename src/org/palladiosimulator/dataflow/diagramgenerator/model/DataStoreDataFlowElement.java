package org.palladiosimulator.dataflow.diagramgenerator.model;

public class DataStoreDataFlowElement extends DataFlowElement {

	public DataStoreDataFlowElement(String id, Boolean isCalling, String name) {
		super(id, isCalling, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(DataFlowElementVisitor visitor) {
		visitor.visit(this);
	}
}
