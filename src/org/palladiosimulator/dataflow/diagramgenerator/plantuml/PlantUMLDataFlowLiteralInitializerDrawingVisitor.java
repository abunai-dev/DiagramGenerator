package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteralVisitor;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;

public class PlantUMLDataFlowLiteralInitializerDrawingVisitor implements DataFlowLiteralVisitor {
	private String drawResult;

	public String getDrawResult() {
		return this.drawResult;
	}

	@Override
	public void visit(DataFlowLiteral literal) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(literal);

		result += "file " + uniqueIdentifier + " #line.dotted [\n";
		result += literal.getTypeName() + ": " + literal.getLiteralName() + "\n";
		result += "]\n";

		this.drawResult = result;
	}
}
