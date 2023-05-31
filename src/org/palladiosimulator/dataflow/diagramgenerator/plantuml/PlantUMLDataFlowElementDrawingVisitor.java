package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVisitor;

public abstract class PlantUMLDataFlowElementDrawingVisitor implements DataFlowElementVisitor {
	private String drawResult;

	public PlantUMLDataFlowElementDrawingVisitor() {
		super();

		this.drawResult = "";
	}

	public String getDrawResult() {
		return this.drawResult;
	}

	public void setDrawResult(String drawResult) {
		this.drawResult = drawResult;
	}
}