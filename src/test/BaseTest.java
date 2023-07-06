package test;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.dataflow.diagramgenerator.DataFlowGraphProcessor;
import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.StandaloneDiagramGenerator;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLDrawingStrategy;

public class BaseTest {
	public static String PROJECT_NAME = "org.palladiosimulator.dataflow.confidentiality.analysis.testmodels";
	public static String USAGE_MODEL_PATH = "models/CoronaWarnApp/default.usagemodel";
	public static String ALLOCATION_PATH = "models/CoronaWarnApp/default.allocation";
	public static String CHARACTERISTICS_PATH = "models/CoronaWarnApp/default.nodecharacteristics";

	@Test
	void runGeneratorWithCoronaWarnApp() {
		GeneratorOptions options = GeneratorOptions.getInstance();
		options.setProjectName(PROJECT_NAME);
		options.setUsageModelPath(USAGE_MODEL_PATH);
		options.setAllocationPath(ALLOCATION_PATH);
		options.setCharacteristicsPath(CHARACTERISTICS_PATH);

		StandaloneDiagramGenerator diagramGenerator = new StandaloneDiagramGenerator(options);

		PlantUMLDrawingStrategy drawer = new PlantUMLDrawingStrategy();
		DataFlowElementFactory creator = DataFlowElementFactory.getInstance();
		DataFlowGraphProcessor processor = new DataFlowGraphProcessor(creator);

		diagramGenerator.generateDataFlowDiagram(drawer, creator, processor);
	}
}
