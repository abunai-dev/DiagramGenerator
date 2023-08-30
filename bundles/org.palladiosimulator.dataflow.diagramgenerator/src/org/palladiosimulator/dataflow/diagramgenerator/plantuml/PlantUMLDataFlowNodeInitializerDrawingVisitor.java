package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;

public class PlantUMLDataFlowNodeInitializerDrawingVisitor extends PlantUMLDataFlowNodeDrawingVisitor {

	@Override
	public void visit(DataFlowNode node) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(node.getElement());
		boolean isViolation = node.getElement().isViolation();

		String colorAddon = "";
		if (isViolation) {
			colorAddon = "color = \"0.877 0.9 0.64\";\n    fontcolor = \"1.000 0.79 0.635\";\n    penwidth = 5.0;";
		}

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
				""", uniqueIdentifier, colorAddon, node.getId());

		this.setDrawResult(result);
	}
}
