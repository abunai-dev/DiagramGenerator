package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNodeVisitor;

public class PlantUMLDataFlowLiteralEmbeddingDrawingVisitor implements DataFlowNodeVisitor {
	private String source;

	public PlantUMLDataFlowLiteralEmbeddingDrawingVisitor(String source) {
		super();
		this.source = source;
	}

	public String getDrawResult() {
		return this.source;
	}

	@Override
	public void visit(DataFlowNode node) {
		PlantUMLDataFlowElementLiteralStringBuilderVisitor stringVisitor = new PlantUMLDataFlowElementLiteralStringBuilderVisitor(
				source, node);
		source = (String) node.getElement().accept(stringVisitor);
	}
}
