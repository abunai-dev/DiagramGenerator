package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.ProcessDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.TerminatorDataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.WarehouseDataFlowElement;

public class PlantUMLDataFlowElementInitializerDrawingVisitor extends PlantUMLDataFlowElementDrawingVisitor {

	@Override
	public void visit(ProcessDataFlowElement element) {
		this.drawElement("usecase", element);
	}

	@Override
	public void visit(TerminatorDataFlowElement element) {
		this.drawElement("actor", element);
	}

	@Override
	public void visit(WarehouseDataFlowElement element) {
		this.drawElement("rectangle", element);
	}

	private void drawElement(String elementType, DataFlowElement element) {
		String result = "";

		String uniqueIdentifier = PlantUMLDataFlowElementUtils.generateUniqueIdentifier(element);

		result += elementType + " " + uniqueIdentifier + " [\n";
		result += element.getName() + "\n";
		result += "----\n";
		result += element.getId() + "\n";
		result += "....\n";
		result += "isCalling: " + element.getIsCalling();
		result += "]\n";

		this.setDrawResult(result);
	}
}
