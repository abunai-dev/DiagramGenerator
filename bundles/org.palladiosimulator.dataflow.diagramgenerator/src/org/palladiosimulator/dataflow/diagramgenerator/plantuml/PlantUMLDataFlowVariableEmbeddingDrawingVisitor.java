package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNodeVisitor;

public class PlantUMLDataFlowVariableEmbeddingDrawingVisitor implements DataFlowNodeVisitor {
	private String source;

	public PlantUMLDataFlowVariableEmbeddingDrawingVisitor(String source) {
		this.source = source;
	}

	public String getDrawResult() {
		return this.source;
	}

	@Override
	public void visit(DataFlowNode node) {
		PlantUMLDataFlowElementVariableStringBuilderVisitor stringVisitor = new PlantUMLDataFlowElementVariableStringBuilderVisitor(
				this.source, node);
		this.source = (String) node.getElement().accept(stringVisitor);
	}

}
