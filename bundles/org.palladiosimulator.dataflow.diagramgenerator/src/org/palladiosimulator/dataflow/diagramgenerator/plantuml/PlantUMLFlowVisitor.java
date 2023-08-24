package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.model.ControlFlow;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlow;
import org.palladiosimulator.dataflow.diagramgenerator.model.FlowVisitor;

public class PlantUMLFlowVisitor implements FlowVisitor<String> {
	private GeneratorOptions options = GeneratorOptions.getInstance();

	@Override
	public String visit(ControlFlow flow) {
		String result = "";

		String currentUID = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(flow.getChild().getElement());
		String parentUID = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(flow.getParent().getElement());

		if (options.isDrawControlFlow()) {
			result += String.format("""
					%s -> %s [style=dotted];
					""", parentUID, currentUID);
		}
		return result;
	}

	@Override
	public String visit(DataFlow flow) {
		String result = "";

		String currentUID = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(flow.getChild().getElement());
		String parentUID = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(flow.getParent().getElement());

		String parameterString = "";

		for (String parameter : flow.getParameters()) {
			if (parameterString.length() > 0) {
				parameterString += ", " + parameter;
			} else {
				parameterString = parameter;
			}
		}

		if (options.isDrawParameters()) {
			result += String.format("""
					%s -> %s [label="%s"];
					""", parentUID, currentUID, parameterString);
		} else {
			result += String.format("""
					%s -> %s;
					""", parentUID, currentUID);
		}

		return result;
	}

}
