package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteralVisitor;

public class PlantUMLDataFlowLiteralInitializerDrawingVisitor implements DataFlowLiteralVisitor {
	private String drawResult;

	public String getDrawResult() {
		return this.drawResult;
	}

	@Override
	public void visit(DataFlowLiteral literal) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(literal);

		result += "file " + uniqueIdentifier + " as \"" + literal.getTypeName() + ": " + literal.getLiteralName()
				+ "\" #line.dotted\n";

		this.drawResult = result;
	}
}
