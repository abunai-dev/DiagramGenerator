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

		int elementLineIndex = source.indexOf(elementIdentifier);
		int afterTitleLineIndex = source.indexOf("// title end", elementLineIndex);
		int afterCharacteristicsLineIndex = source.indexOf("// characteristics end", afterTitleLineIndex);

		StringBuilder sb = new StringBuilder(source);

		for (DataFlowLiteral literal : node.getLiterals()) {
			String toInsert = String.format("""
					<tr>
					    <td colspan="3" border="0">%s.%s </td>
					</tr>
					""", literal.getTypeName(), literal.getLiteralName());

			if (afterCharacteristicsLineIndex != -1) {
				sb.insert(afterCharacteristicsLineIndex, "\n" + toInsert);
			} else {
				sb.append("\n").append(toInsert);
			}
		}

		source = sb.toString();
	}
}
