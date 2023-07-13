package test;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMDataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMDiagramGenerator;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMGraphProcessor;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLDrawingStrategy;

public class BaseTest {
	public static String PROJECT_NAME = "org.palladiosimulator.dataflow.confidentiality.analysis.testmodels";
	public static String USAGE_MODEL_PATH = "models/TravelPlannerNew/travelPlanner.usagemodel";
	public static String ALLOCATION_PATH = "models/TravelPlannerNew/travelPlanner.allocation";
	public static String CHARACTERISTICS_PATH = "models/TravelPlannerNew/travelPlanner.nodecharacteristics";

	@Test
	void runGeneratorWithCoronaWarnApp() {
		GeneratorOptions options = GeneratorOptions.getInstance();
		options.setProjectName(PROJECT_NAME);
		options.setUsageModelPath(USAGE_MODEL_PATH);
		options.setAllocationPath(ALLOCATION_PATH);
		options.setCharacteristicsPath(CHARACTERISTICS_PATH);
		options.setDrawControlFlow(true);

		PCMDiagramGenerator diagramGenerator = new PCMDiagramGenerator(options);

		PlantUMLDrawingStrategy drawer = new PlantUMLDrawingStrategy();
		PCMDataFlowElementFactory creator = PCMDataFlowElementFactory.getInstance();
		PCMGraphProcessor processor = new PCMGraphProcessor(creator);

		diagramGenerator.generateDataFlowDiagram(drawer, processor);
	}
}
