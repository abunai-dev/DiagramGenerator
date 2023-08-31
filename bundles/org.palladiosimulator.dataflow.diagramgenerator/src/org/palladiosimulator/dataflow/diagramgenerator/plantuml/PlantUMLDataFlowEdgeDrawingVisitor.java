package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNodeVisitor;
import org.palladiosimulator.dataflow.diagramgenerator.model.Flow;

public class PlantUMLDataFlowEdgeDrawingVisitor implements DataFlowNodeVisitor {
	private String drawResult;

	public String getDrawResult() {
		return this.drawResult;
	}

	@Override
	public void visit(DataFlowNode node) {
		String result = "";

		if (node.getParentFlows().size() > 0) {
			PlantUMLFlowVisitor visitor = new PlantUMLFlowVisitor();

			for (Flow parent : node.getParentFlows()) {
				result += parent.accept(visitor);
			}
		}

		this.drawResult = result;
	}
}
