package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariable;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariableVisitor;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;

public class PlantUMLDataFlowVariableLiteralEmbeddingDrawingVisitor implements DataFlowElementVariableVisitor {
	private String source;

	public PlantUMLDataFlowVariableLiteralEmbeddingDrawingVisitor(String source) {
		super();
		this.source = source;
	}

	public String getDrawResult() {
		return this.source;
	}

	@Override
	public void visit(DataFlowElementVariable variable) {
		if (variable.getLiterals().size() > 0) {
			String elementIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(variable);

			int nextNewLineIndex = source.indexOf("\n", source.indexOf(elementIdentifier));
			// if character before newline is { then no new parentheses are needed
			boolean hasParentheses = source.charAt(nextNewLineIndex - 1) == '{' ? true : false;

			String insertion = hasParentheses ? "\n" : "{\n";

			for (DataFlowLiteral literal : variable.getLiterals()) {
				String literalIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(literal);

				insertion += "hexagon " + literalIdentifier + " as \"" + literal.getTypeName() + ": "
						+ literal.getLiteralName() + "\" #line.dotted\n";
			}

			insertion += hasParentheses ? "" : "}\n";

			this.source = source.substring(0, nextNewLineIndex) + insertion + source.substring(nextNewLineIndex + 1);
		}
	}
}
