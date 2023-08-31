package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNodeVisitor;

public abstract class PlantUMLDataFlowNodeDrawingVisitor implements DataFlowNodeVisitor {
	private String drawResult;

	public PlantUMLDataFlowNodeDrawingVisitor() {
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