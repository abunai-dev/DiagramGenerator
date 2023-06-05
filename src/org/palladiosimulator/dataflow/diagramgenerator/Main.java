package org.palladiosimulator.dataflow.diagramgenerator;

import org.palladiosimulator.dataflow.diagramgenerator.ui.UIDesigner;

public class Main {
	public static void main(String[] args) {
		if (args.length > 0) {
			// If command-line arguments are provided, run the analysis directly
			CommandLineOptions options = CommandLineParser.parseCommandLineOptions(args);
			if (options != null) {
				StandaloneDiagramGenerator diagramGenerator = new StandaloneDiagramGenerator(options);
				diagramGenerator.runDataFlowDiagramGeneration();
			}
		} else {
			// Otherwise, create the UI
			UIDesigner.createUI();
		}
	}

}
