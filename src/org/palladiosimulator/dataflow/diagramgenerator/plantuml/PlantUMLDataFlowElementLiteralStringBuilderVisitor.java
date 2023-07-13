package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVisitor;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataStoreDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.ExternalEntityDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.ProcessDataFlowElement;

public class PlantUMLDataFlowElementLiteralStringBuilderVisitor implements DataFlowElementVisitor<String> {
	private String source;
	private DataFlowNode node;

	public PlantUMLDataFlowElementLiteralStringBuilderVisitor(String source, DataFlowNode node) {
		this.source = source;
		this.node = node;
	}

	@Override
	public String visit(ProcessDataFlowElement element) {
		return this.visitMainCase();
	}

	@Override
	public String visit(ExternalEntityDataFlowElement element) {
		return this.visitMainCase();
	}

	@Override
	public String visit(DataStoreDataFlowElement element) {
		String elementIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(node.getElement());

		int elementLineIndex = source.indexOf(elementIdentifier);
		int afterTitleLineIndex = source.indexOf("// title end", elementLineIndex);
		int afterCharacteristicsLineIndex = source.indexOf("// characteristics end", afterTitleLineIndex);

		StringBuilder sb = new StringBuilder(source);

		for (DataFlowLiteral literal : node.getLiterals()) {
			String toInsert = String.format("""
					<tr>
						<td colspan="1" border="1" sides="r"> </td>
					    <td colspan="2" border="0">%s.%s </td>
					    <td colspan="1" border="1" sides="l"> </td>
					</tr>
					""", literal.getTypeName(), literal.getLiteralName());

			if (afterCharacteristicsLineIndex != -1) {
				sb.insert(afterCharacteristicsLineIndex, "\n" + toInsert);
			} else {
				sb.append("\n").append(toInsert);
			}
		}

		return sb.toString();
	}
	
	private String visitMainCase() {
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

		return sb.toString();
	}
}
