package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariable;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVisitor;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataStoreDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.ExternalEntityDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.ProcessDataFlowElement;

public class PlantUMLDataFlowElementVariableStringBuilderVisitor implements DataFlowElementVisitor<String> {
	private String source;
	private DataFlowNode node;

	public PlantUMLDataFlowElementVariableStringBuilderVisitor(String source, DataFlowNode node) {
		this.source = source;
		this.node = node;
	}

	@Override
	public String visit(ProcessDataFlowElement element) {
		return this.visitMainCase(element);
	}

	@Override
	public String visit(ExternalEntityDataFlowElement element) {
		return this.visitMainCase(element);
	}

	@Override
	public String visit(DataStoreDataFlowElement element) {
		String elementIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(element);
		int elementLineIndex = source.indexOf(elementIdentifier);
		StringBuilder sb = new StringBuilder(source);
		int varCounter = 0;

		for (DataFlowElementVariable variable : node.getVariables()) {
			int litCounter = 0;
			for (DataFlowLiteral literal : node.getLiterals()) {
				int charEndLineIndex = sb.indexOf("// characteristics end", elementLineIndex);
				int varEndLineIndex = sb.indexOf("// variables end", charEndLineIndex);

				String prefix = litCounter > 0 ? "" : variable.getName().concat(":");
				String toInsert = String.format("""
						<tr>
							<td colspan="1" border="1" sides="r"> </td>
						    <td colspan="1" %s>%s</td>
						    <td colspan="1" %s>%s.%s</td>
						    <td colspan="1" border="1" sides="l"> </td>
						</tr>
						""", litCounter < 1 && varCounter < 1 ? "sides=\"t\"" : "border=\"0\"", prefix,
						litCounter < 1 && varCounter < 1 ? "sides=\"t\"" : "border=\"0\"", literal.getTypeName(),
						literal.getLiteralName());

				sb.insert(varEndLineIndex, toInsert);
				litCounter++;
			}
			varCounter++;
		}

		return sb.toString();
	}

	private String visitMainCase(DataFlowElement element) {
		String elementIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(element);
		int elementLineIndex = source.indexOf(elementIdentifier);
		StringBuilder sb = new StringBuilder(source);
		int varCounter = 0;

		for (DataFlowElementVariable variable : node.getVariables()) {
			int litCounter = 0;
			for (DataFlowLiteral literal : variable.getLiterals()) {
				int charEndLineIndex = sb.indexOf("// characteristics end", elementLineIndex);
				int varEndLineIndex = sb.indexOf("// variables end", charEndLineIndex);

				String prefix = litCounter > 0 ? "" : variable.getName().concat(":");
				String toInsert = String.format("""
						<tr>
						    <td %s>%s</td>
						    <td %s>%s.%s</td>
						</tr>
						""", litCounter < 1 && varCounter < 1 ? "sides=\"t\"" : "border=\"0\"", prefix,
						litCounter < 1 && varCounter < 1 ? "sides=\"t\"" : "border=\"0\"", literal.getTypeName(),
						literal.getLiteralName());

				sb.insert(varEndLineIndex, toInsert);
				litCounter++;
			}
			varCounter++;
		}

		return sb.toString();
	}
}
