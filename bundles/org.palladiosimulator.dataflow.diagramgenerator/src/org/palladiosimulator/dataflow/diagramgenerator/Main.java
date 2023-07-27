package org.palladiosimulator.dataflow.diagramgenerator;

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
			//generateDataFlowDiagram();
		}
	}

	/*
	 * private static void generateDataFlowDiagram() { GeneratorOptions options =
	 * GeneratorOptions.getInstance(); PCMDiagramGenerator diagramGenerator = new
	 * PCMDiagramGenerator(options); PlantUMLDrawingStrategy drawer = new
	 * PlantUMLDrawingStrategy(); PCMDataFlowElementFactory creator =
	 * PCMDataFlowElementFactory.getInstance(); PCMGraphProcessor processor = new
	 * PCMGraphProcessor(creator); diagramGenerator.generateDataFlowDiagram(drawer,
	 * processor); }
	 */

	private static void showUserInterface() {
		UIDesigner.createUI();
	}
}
