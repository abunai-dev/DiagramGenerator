package org.palladiosimulator.dataflow.diagramgenerator;

public class PlantUMLUtility {
	public static String concatActivityDiagramElements(String firstEntity, String secondEntity, String label) {
		if (label == null) {
			return "\"" + firstEntity + "\" --> \"" + secondEntity + "\"\n";
		} else {
			return "\"" + firstEntity + "\" --> [" + label + "] \"" + secondEntity + "\"\n";
		}
	}
}
