package org.palladiosimulator.dataflow.diagramgenerator.tests;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMDataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMDiagramGenerator;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMGraphProcessor;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLDrawingStrategy;
import org.palladiosimulator.dataflow.diagramgenerator.testmodels.Activator;

public class BaseTest {
	public static String PROJECT_NAME = "org.palladiosimulator.dataflow.diagramgenerator.testmodels";
	public static String USAGE_MODEL_PATH = "casestudies/CaseStudy-CoronaWarnApp/CoronaWarnApp/default.usagemodel";
	public static String ALLOCATION_PATH = "casestudies/CaseStudy-CoronaWarnApp/CoronaWarnApp/default.allocation";
	public static String CHARACTERISTICS_PATH = "casestudies/CaseStudy-CoronaWarnApp/CoronaWarnApp/default.nodecharacteristics";

	@Test
	void runGeneratorWithModel() {
		GeneratorOptions options = GeneratorOptions.getInstance();
		options.setProjectName(PROJECT_NAME);
		options.setUsageModelPath(USAGE_MODEL_PATH);
		options.setAllocationPath(ALLOCATION_PATH);
		options.setCharacteristicsPath(CHARACTERISTICS_PATH);
		options.setDrawControlFlow(false);

		PCMDiagramGenerator diagramGenerator = new PCMDiagramGenerator(options, Activator.class);

		PlantUMLDrawingStrategy drawer = new PlantUMLDrawingStrategy();
		PCMDataFlowElementFactory creator = PCMDataFlowElementFactory.getInstance();
		PCMGraphProcessor processor = new PCMGraphProcessor(creator);

		diagramGenerator.generateDataFlowDiagram(drawer, processor);
	}
}
