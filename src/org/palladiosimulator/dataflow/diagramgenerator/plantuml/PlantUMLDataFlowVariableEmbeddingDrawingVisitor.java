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
		String elementIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(node.getElement());

		int elementLineIndex = source.indexOf(elementIdentifier);

		StringBuilder sb = new StringBuilder(source);

		int varCounter = 0;
		for (DataFlowElementVariable variable : node.getVariables()) {
			int litCounter = 0;
			for (DataFlowLiteral literal : node.getLiterals()) {
				int charEndLineIndex = sb.indexOf("// characteristics end", elementLineIndex);
				int varEndLineIndex = sb.indexOf("// variables end", charEndLineIndex);

				String toInsert = "";
				if (varCounter < 1) {
					toInsert = String.format("""
							           <tr>
							               <td sides="t">%s</td>
							               <td sides="t">%s.%s </td>
							           </tr>
							""", litCounter > 1 ? "" : variable.getName().concat(":"), literal.getTypeName(),
							literal.getLiteralName());
				} else {
					toInsert = String.format("""
							           <tr>
							               <td border="0">%s</td>
							               <td border="0">%s.%s </td>
							           </tr>
							""", litCounter > 1 ? "" : variable.getName().concat(":"), literal.getTypeName(),
							literal.getLiteralName());
				}

				sb.insert(varEndLineIndex, toInsert);

				litCounter++;
			}
			varCounter++;
		}

		source = sb.toString();
	}
}
