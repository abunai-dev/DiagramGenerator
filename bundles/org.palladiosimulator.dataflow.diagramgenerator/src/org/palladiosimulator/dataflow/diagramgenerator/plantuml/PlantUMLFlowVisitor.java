package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.model.ControlFlow;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlow;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.Flow;
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
					%s -> %s [style=dotted;%s];
					""", parentUID, currentUID, this.getColorAddon(flow));
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
					%s -> %s [label="%s";%s];
					""", parentUID, currentUID, parameterString, this.getColorAddon(flow));
		} else {
			result += String.format("""
					%s -> %s%s;
					""", parentUID, currentUID,
					this.getColorAddon(flow).equals("") ? "" : "[".concat(this.getColorAddon(flow)).concat("]"));
		}

		return result;
	}

	private String getColorAddon(Flow flow) {
		if (flow.getParent().getElement().isHasUncertainty()) {
			return "color = \"0.877 0.9 0.64\";fontcolor = \"0.877 0.9 0.64\";penwidth = 5.0;";
		}

		return "";
	}
}
