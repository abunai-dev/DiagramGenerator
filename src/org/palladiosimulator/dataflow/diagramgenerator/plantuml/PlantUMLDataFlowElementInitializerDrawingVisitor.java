package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.ProcessDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.ExternalEntityDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.WarehouseDataFlowElement;

public class PlantUMLDataFlowElementInitializerDrawingVisitor extends PlantUMLDataFlowElementDrawingVisitor {

	@Override
	public void visit(ProcessDataFlowElement element) {
		this.drawElement("usecase", element);
	}

	@Override
	public void visit(ExternalEntityDataFlowElement element) {
		this.drawElement("agent", element);
	}

	@Override
	public void visit(WarehouseDataFlowElement element) {
		this.drawElement("database", element);
	}

	private void drawElement(String elementType, DataFlowElement element) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(element);

		result += elementType + " " + uniqueIdentifier + " [\n";
		result += element.getName() + "\n";
		result += "]\n";

		this.setDrawResult(result);
	}
}
