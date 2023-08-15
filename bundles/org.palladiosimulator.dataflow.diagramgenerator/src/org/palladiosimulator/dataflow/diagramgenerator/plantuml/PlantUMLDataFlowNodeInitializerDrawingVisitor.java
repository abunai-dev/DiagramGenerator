package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;

public class PlantUMLDataFlowNodeInitializerDrawingVisitor extends PlantUMLDataFlowNodeDrawingVisitor {

	@Override
	public void visit(DataFlowNode node) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(node.getElement());
		boolean isViolation = node.getElement().isViolation();

		result += String.format("""
				"%s" [
				    shape = circle;margin=0;padding=0;
				    %s
				    label =
				    <
				        <table border="0" cellspacing="0" cellborder="1">
				            <tr>
				                <td colspan="3" border="0"><b>%s</b>  </td>
				            </tr>
				            // title end
				            // characteristics end
				            // variables end
				        </table>
				    >
				];
				""", uniqueIdentifier, isViolation ? "color = red;" : "", node.getId());

		this.setDrawResult(result);
	}
}
