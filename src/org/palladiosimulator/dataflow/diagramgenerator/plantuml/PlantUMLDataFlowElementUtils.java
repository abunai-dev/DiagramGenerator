package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;

public class PlantUMLDataFlowElementUtils {
	public static String generateUniqueIdentifier(DataFlowElement element) {
		int hashCode = Math.abs(element.hashCode());
		return String.valueOf(hashCode);
	}
	
	public static String generateUniqueIdentifier(DataFlowLiteral literal) {
		int hashCode = Math.abs(literal.hashCode());
		return String.valueOf(hashCode);
	}
}
