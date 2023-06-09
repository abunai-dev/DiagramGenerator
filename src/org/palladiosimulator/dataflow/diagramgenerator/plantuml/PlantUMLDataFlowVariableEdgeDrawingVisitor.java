package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariable;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNodeVisitor;

public class PlantUMLDataFlowVariableEdgeDrawingVisitor implements DataFlowNodeVisitor {
	private String drawResult;

	public String getDrawResult() {
		return this.drawResult;
	}

	@Override
	public void visit(DataFlowNode node) {
		String result = "";

		String elementIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(node.getElement());

		for (DataFlowElementVariable variable : node.getVariables()) {
			String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(variable);

			result += elementIdentifier + " .. " + uniqueIdentifier + "\n";
		}

		this.drawResult = result;
	}
}
