package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
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
		String elementIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(node.getElement());

		int nextNewLineIndex = source.indexOf("\n", source.indexOf(elementIdentifier));
		// if character before newline is { then no new parentheses are needed
		boolean hasParentheses = source.charAt(nextNewLineIndex - 1) == '{' ? true : false;

		String insertion = "";

		insertion += hasParentheses ? "\n" : "{\n";

		for (DataFlowLiteral literal : node.getLiterals()) {
			String literalIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(literal);

			insertion += "hexagon " + literalIdentifier + " as \"" + literal.getTypeName() + ": "
					+ literal.getLiteralName() + "\" #line.dotted\n";
		}

		insertion += hasParentheses ? "" : "}\n";

		var part1 = source.substring(0, nextNewLineIndex);
		var part2 = source.substring(nextNewLineIndex + 1);

		this.source = source.substring(0, nextNewLineIndex) + insertion + source.substring(nextNewLineIndex + 1);
		var i = 1;
	}
}
