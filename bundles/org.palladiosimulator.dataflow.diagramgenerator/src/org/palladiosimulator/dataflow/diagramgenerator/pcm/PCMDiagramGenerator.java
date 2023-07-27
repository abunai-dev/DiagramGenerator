package org.palladiosimulator.dataflow.diagramgenerator.pcm;

import java.util.List;

import org.eclipse.core.runtime.Plugin;
import org.palladiosimulator.dataflow.confidentiality.analysis.DataFlowConfidentialityAnalysis;
import org.palladiosimulator.dataflow.confidentiality.analysis.builder.DataFlowAnalysisBuilder;
import org.palladiosimulator.dataflow.confidentiality.analysis.builder.pcm.PCMDataFlowConfidentialityAnalysisBuilder;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.ActionSequence;
import org.palladiosimulator.dataflow.diagramgenerator.DiagramGenerator;
import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DrawingStrategy;

/**
 * The StandaloneDiagramGenerator class is responsible for generating a data
 * flow diagram based on the provided GeneratorOptions.
 */
public class PCMDiagramGenerator implements DiagramGenerator<PCMGraphProcessor> {
	private GeneratorOptions options;
	private DataFlowConfidentialityAnalysis analysis;
	private Class<? extends Plugin> activatorClass;

	/**
	 * Constructs a StandaloneDiagramGenerator with the given GeneratorOptions.
	 * 
	 * @param options The GeneratorOptions for generating the data flow diagram.
	 * @throws IllegalArgumentException if the provided options are invalid.
	 */
	public PCMDiagramGenerator(GeneratorOptions options, Class<? extends Plugin> activatorClass) {
		if (!options.isValid()) {
			throw new IllegalArgumentException("Invalid command line options. Aborting.");
		}
		this.options = options;
		this.activatorClass = activatorClass;

		initializeAnalysis();
	}

	/**
	 * Generates the data flow diagram using the provided DrawingStrategy,
	 * DataFlowElementFactory, and DataFlowGraphProcessor.
	 * 
	 * @param drawer         The DrawingStrategy for generating the diagram.
	 * @param elementCreator The DataFlowElementFactory for creating data flow
	 *                       elements.
	 * @param graphProcessor The DataFlowGraphProcessor for processing action
	 *                       sequences.
	 */
	public void generateDataFlowDiagram(DrawingStrategy drawer, PCMGraphProcessor graphProcessor) {
		List<ActionSequence> actionSequences = getActionSequences();

		List<DataFlowNode> dataFlowNodes = graphProcessor.processActionSequences(actionSequences);
		System.out.println("Model translation finished!");

		drawer.generate(dataFlowNodes);
		drawer.saveToDisk("output/data-flow.svg");
		System.out.println("Done!");
	}

	private List<ActionSequence> getActionSequences() {
		List<ActionSequence> actionSequences = analysis.findAllSequences();
		return analysis.evaluateDataFlows(actionSequences);
	}

	private void initializeAnalysis() {
		String projectName = options.getProjectName();
		String usageModelPath = options.getUsageModelPath();
		String allocationPath = options.getAllocationPath();
		String characteristicsPath = options.getCharacteristicsPath();

		this.analysis = new DataFlowAnalysisBuilder().standalone().modelProjectName(projectName)
				.useBuilder(new PCMDataFlowConfidentialityAnalysisBuilder()).usePluginActivator(this.activatorClass)
				.useUsageModel(usageModelPath).useAllocationModel(allocationPath)
				.useNodeCharacteristicsModel(characteristicsPath).build();

		try {
			analysis.initializeAnalysis();
		} catch (Exception e) {
			this.analysis = new DataFlowAnalysisBuilder().standalone().modelProjectName(projectName)
					.useBuilder(new PCMDataFlowConfidentialityAnalysisBuilder()).legacy()
					.usePluginActivator(this.activatorClass).useUsageModel(usageModelPath)
					.useAllocationModel(allocationPath).build();
			analysis.initializeAnalysis();
		}

		System.out.println("Initialization finished!");
	}
}
