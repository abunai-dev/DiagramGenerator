package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariable;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariableVisitor;

public class PlantUMLDataFlowElementVariableInitializerVisitor implements DataFlowElementVariableVisitor {
	private String drawResult;

	public String getDrawResult() {
		return this.drawResult;
	}

	@Override
	public void visit(DataFlowElementVariable variable) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(variable);

		result += "artifact " + uniqueIdentifier + " #line.dotted [\n";
		result += variable.getName() + "\n";
		result += "]\n";

		this.drawResult = result;
	}

}
