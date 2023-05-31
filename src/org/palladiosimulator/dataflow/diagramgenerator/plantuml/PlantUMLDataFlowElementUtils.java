package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;

public class PlantUMLDataFlowElementUtils {
	public static String generateUniqueIdentifier(DataFlowElement element) {
		int hashCode = Math.abs(element.hashCode());
		return String.valueOf(hashCode);
	}
}
