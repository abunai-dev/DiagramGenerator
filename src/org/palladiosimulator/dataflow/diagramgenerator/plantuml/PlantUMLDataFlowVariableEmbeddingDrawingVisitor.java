package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariable;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNodeVisitor;

public class PlantUMLDataFlowVariableEmbeddingDrawingVisitor implements DataFlowNodeVisitor {
	private String source;

	public PlantUMLDataFlowVariableEmbeddingDrawingVisitor(String source) {
		this.source = source;
	}

	public String getDrawResult() {
		return this.source;
	}

	@Override
	public void visit(DataFlowNode node) {
		if (node.getVariables().size() > 0) {
			String elementIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(node.getElement());

			int nextNewLineIndex = source.indexOf("\n", source.indexOf(elementIdentifier));
			// if character before newline is { then no new parentheses are needed
			boolean hasParentheses = source.charAt(nextNewLineIndex - 1) == '{' ? true : false;

			String insertion = hasParentheses ? "\n" : "{\n";

			for (DataFlowElementVariable variable : node.getVariables()) {
				String literalIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(variable);

				insertion += "file " + literalIdentifier + " as \"" + variable.getName() + "\" #line.dotted\n";
			}

			insertion += hasParentheses ? "" : "}\n";

			this.source = source.substring(0, nextNewLineIndex) + insertion + source.substring(nextNewLineIndex + 1);
		}
	}
}
