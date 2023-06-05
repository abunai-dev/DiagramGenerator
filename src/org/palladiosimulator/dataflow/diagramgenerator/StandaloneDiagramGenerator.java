package org.palladiosimulator.dataflow.diagramgenerator;

import java.util.List;

import org.palladiosimulator.dataflow.confidentiality.analysis.DataFlowConfidentialityAnalysis;
import org.palladiosimulator.dataflow.confidentiality.analysis.builder.DataFlowAnalysisBuilder;
import org.palladiosimulator.dataflow.confidentiality.analysis.builder.pcm.PCMDataFlowConfidentialityAnalysisBuilder;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.ActionSequence;
import org.palladiosimulator.dataflow.confidentiality.analysis.testmodels.Activator;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DrawingStrategy;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLDrawingStrategy;

public class StandaloneDiagramGenerator {
	private String projectName;
	private String usageModelPath;
	private String allocationPath;

	public StandaloneDiagramGenerator(CommandLineOptions options) {
		if (!options.isValid()) {
			System.err.println("Invalid command line options. Aborting.");
			return;
		}

		this.projectName = options.getProjectName();
		this.usageModelPath = options.getUsageModelPath();
		this.allocationPath = options.getAllocationPath();
	}

	private List<ActionSequence> initializeAnalysisAndGetActionSequences(String projectName, String usageModelPath,
			String allocationPath) throws AnalysisInitializationException {
		DataFlowConfidentialityAnalysis analysis = new DataFlowAnalysisBuilder()
        		.standalone()
        		.modelProjectName(projectName)
        		.useBuilder(new PCMDataFlowConfidentialityAnalysisBuilder())
        		.legacy()
        		.usePluginActivator(Activator.class)
        		.useUsageModel(usageModelPath)
        		.useAllocationModel(allocationPath)
        		.build();
		analysis.initializeAnalysis();

		return analysis.findAllSequences();
	}

	public void runDataFlowDiagramGeneration() {
		try {
			List<ActionSequence> actionSequences = initializeAnalysisAndGetActionSequences(this.projectName,
					this.usageModelPath, this.allocationPath);
			System.out.println("Initialization finished!");

			DataFlowElementFactory elementCreator = DataFlowElementFactory.getInstance();
			DataFlowGraphProcessor graphProcessor = new DataFlowGraphProcessor(elementCreator);
			List<DataFlowNode> dataFlowNodes = graphProcessor.processActionSequences(actionSequences);
			System.out.println("Model translation finished!");

			DrawingStrategy drawer = new PlantUMLDrawingStrategy();
			drawer.generate(dataFlowNodes);
			drawer.saveToDisk("output/data-flow.svg");
			System.out.println("Done!");
		} catch (AnalysisInitializationException e) {
			System.err.println("Error initializing the analysis: " + e.getMessage());
		}
	}
}
