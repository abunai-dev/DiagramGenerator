package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariable;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariableVisitor;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;

public class PlantUMLDataFlowVariableLiteralEdgeDrawingVisitor implements DataFlowElementVariableVisitor {
	private String drawResult;

	public String getDrawResult() {
		return this.drawResult;
	}

	@Override
	public void visit(DataFlowElementVariable variable) {
		String result = "";

		String variableIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(variable);

		for (DataFlowLiteral literal : variable.getLiterals()) {
			String literalIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(literal);

			result += variableIdentifier + " .. " + literalIdentifier + "\n";
		}

		this.drawResult = result;
	}
}
