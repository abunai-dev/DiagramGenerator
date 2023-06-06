package org.palladiosimulator.dataflow.diagramgenerator;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLDrawingStrategy;
import org.palladiosimulator.dataflow.diagramgenerator.ui.UIDesigner;

public class Main {
	public static void main(String[] args) {
		if (args.length > 0) {
			runGenerator(args);
		} else {
			showUserInterface();
		}
	}

	private static void runGenerator(String[] args) {
		GeneratorOptions options = CommandLineParser.parseCommandLineOptions(args);
		if (options != null) {
			generateDataFlowDiagram(options);
		}
	}

	private static void generateDataFlowDiagram(GeneratorOptions options) {
		StandaloneDiagramGenerator diagramGenerator = new StandaloneDiagramGenerator(options);
		PlantUMLDrawingStrategy drawer = new PlantUMLDrawingStrategy();
		DataFlowElementFactory creator = DataFlowElementFactory.getInstance();
		DataFlowGraphProcessor processor = new DataFlowGraphProcessor(creator);
		diagramGenerator.generateDataFlowDiagram(drawer, creator, processor);
	}

	private static void showUserInterface() {
		UIDesigner.createUI();
	}
}
