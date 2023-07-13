package test;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.dataflow.diagramgenerator.PCMGraphProcessor;
import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.StandaloneDiagramGenerator;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementFactory;
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

		StandaloneDiagramGenerator diagramGenerator = new StandaloneDiagramGenerator(options);

		PlantUMLDrawingStrategy drawer = new PlantUMLDrawingStrategy();
		DataFlowElementFactory creator = DataFlowElementFactory.getInstance();
		PCMGraphProcessor processor = new PCMGraphProcessor(creator);

		diagramGenerator.generateDataFlowDiagram(drawer, creator, processor);
	}
}
