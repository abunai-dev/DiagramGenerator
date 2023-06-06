package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNodeVisitor;

public class PlantUMLDataFlowLiteralEdgeDrawingVisitor implements DataFlowNodeVisitor {
	private String drawResult;

	public String getDrawResult() {
		return this.drawResult;
	}

	@Override
	public void visit(DataFlowNode node) {
		String result = "";

		String elementIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(node.getElement());

		for (DataFlowLiteral literal : node.getLiterals()) {
			String literalIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(literal);

			result += elementIdentifier + " .. " + literalIdentifier + "\n";
		}

		this.drawResult = result;
	}

}
