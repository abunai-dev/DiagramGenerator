package org.palladiosimulator.dataflow.diagramgenerator;

import java.util.List;

import org.palladiosimulator.dataflow.confidentiality.analysis.DataFlowConfidentialityAnalysis;
import org.palladiosimulator.dataflow.confidentiality.analysis.builder.DataFlowAnalysisBuilder;
import org.palladiosimulator.dataflow.confidentiality.analysis.builder.pcm.PCMDataFlowConfidentialityAnalysisBuilder;
import org.palladiosimulator.dataflow.confidentiality.analysis.characteristics.CharacteristicValue;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.ActionSequence;
import org.palladiosimulator.dataflow.confidentiality.analysis.testmodels.Activator;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DrawingStrategy;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLDrawingStrategy;
import org.palladiosimulator.dataflow.dictionary.characterized.DataDictionaryCharacterized.Literal;
import org.palladiosimulator.dataflow.dictionary.characterized.DataDictionaryCharacterized.impl.EnumCharacteristicTypeImpl;
import org.palladiosimulator.dataflow.dictionary.characterized.DataDictionaryCharacterized.impl.EnumerationImpl;
import org.palladiosimulator.dataflow.dictionary.characterized.DataDictionaryCharacterized.impl.LiteralImpl;

public class StandaloneDiagramGenerator {
	private String projectName;
	private String usageModelPath;
	private String allocationPath;
	private DataFlowConfidentialityAnalysis analysis;

	public StandaloneDiagramGenerator(GeneratorOptions options) {
		if (!options.isValid()) {
			System.err.println("Invalid command line options. Aborting.");
			return;
		}

		this.initializeAnalysis(options.getProjectName(), options.getUsageModelPath(), options.getAllocationPath());
	}

	private void initializeAnalysis(String projectName, String usageModelPath, String allocationPath) {
		this.analysis = new DataFlowAnalysisBuilder().standalone().modelProjectName(projectName)
				.useBuilder(new PCMDataFlowConfidentialityAnalysisBuilder()).legacy()
				.usePluginActivator(Activator.class).useUsageModel(usageModelPath).useAllocationModel(allocationPath)
				.build();
		analysis.initializeAnalysis();
		System.out.println("Initialization finished!");
	}

	private List<ActionSequence> getActionSequences() {
		List<ActionSequence> actionSequences = this.analysis.findAllSequences();
		return this.analysis.evaluateDataFlows(actionSequences);
	}

	public void runDataFlowDiagramGeneration() {
		List<ActionSequence> actionSequences = this.getActionSequences();

		DataFlowElementFactory elementCreator = DataFlowElementFactory.getInstance();
		DataFlowGraphProcessor graphProcessor = new DataFlowGraphProcessor(elementCreator);
		List<DataFlowNode> dataFlowNodes = graphProcessor.processActionSequences(actionSequences);
		System.out.println("Model translation finished!");

		DrawingStrategy drawer = new PlantUMLDrawingStrategy();
		drawer.generate(dataFlowNodes);
		drawer.saveToDisk("output/data-flow.svg");
		System.out.println("Done!");
	}
}
