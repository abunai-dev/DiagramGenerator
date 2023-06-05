package org.palladiosimulator.dataflow.diagramgenerator.model;

public interface DataFlowElementVisitor {
	void visit(ProcessDataFlowElement element);

	void visit(ExternalEntityDataFlowElement element);

	void visit(WarehouseDataFlowElement element);
}
