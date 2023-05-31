package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNodeVisitor;

public class PlantUMLDataFlowNodeDrawingVisitor implements DataFlowNodeVisitor {
	private String drawResult;

	public String getDrawResult() {
		return this.drawResult;
	}

	@Override
	public void visit(DataFlowNode node) {
		String result = "";

		if (node.getParents().size() > 0) {
			String currentUID = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(node.getElement());

			String parameterString = "";
			for (String parameter : node.getElement().getParameters()) {
				if (parameterString.length() > 0) {
					parameterString += ", " + parameter;
				} else {
					parameterString = parameter;
				}
			}

			for (DataFlowNode parent : node.getParents()) {
				String parentUID = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(parent.getElement());

				if (parameterString.length() > 0) {
					result += parentUID + " --> " + currentUID + " : " + parameterString + "\n";
				} else {
					result += parentUID + " --> " + currentUID + "\n";
				}
			}
		}

		this.drawResult = result;
	}

}
