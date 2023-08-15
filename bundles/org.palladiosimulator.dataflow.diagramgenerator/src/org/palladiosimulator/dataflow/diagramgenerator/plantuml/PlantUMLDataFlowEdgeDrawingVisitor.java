package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNodeVisitor;

public class PlantUMLDataFlowEdgeDrawingVisitor implements DataFlowNodeVisitor {
	private String drawResult;

	public String getDrawResult() {
		return this.drawResult;
	}

	@Override
	public void visit(DataFlowNode node) {
		String result = "";

		if (node.getParents().size() > 0) {
			GeneratorOptions options = GeneratorOptions.getInstance();

			String currentUID = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(node.getElement());

			String parameterString = "";
			for (String parameter : node.getElement().getParameters()) {
				if (parameterString.length() > 0) {
					parameterString += ", " + parameter;
				} else {
					parameterString = parameter;
				}
			}

			for (DataFlowNode parent : node.getParents()) {
				String parentUID = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(parent.getElement());

				if (parameterString.length() > 0) {
					if (options.isDrawParameters()) {
						result += String.format("""
								%s -> %s [label="%s"];
								""", parentUID, currentUID, parameterString);
					} else {
						result += String.format("""
								%s -> %s;
								""", parentUID, currentUID);
					}
				} else if (options.isDrawControlFlow()) {
					result += String.format("""
							%s -> %s [style=dotted];
							""", parentUID, currentUID);
				}
			}
		}

		this.drawResult = result;
	}
}
