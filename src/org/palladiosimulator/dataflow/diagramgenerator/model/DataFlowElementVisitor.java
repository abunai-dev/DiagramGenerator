package org.palladiosimulator.dataflow.diagramgenerator.model;

public interface DataFlowElementVisitor {
	void visit(ProcessDataFlowElement element);

	void visit(TerminatorDataFlowElement element);

	void visit(WarehouseDataFlowElement element);
}
