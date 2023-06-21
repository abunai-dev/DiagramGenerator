package test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.palladiosimulator.dataflow.diagramgenerator.DataFlowGraphProcessor;
import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.StandaloneDiagramGenerator;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLDrawingStrategy;

public class BaseTest {
	public static String TEST_MODEL_PROJECT_NAME = "org.palladiosimulator.dataflow.confidentiality.analysis.testmodels";

	private static StandaloneDiagramGenerator diagramGenerator;

	@BeforeAll
	public static void initializeGenerator() {
		GeneratorOptions options = new GeneratorOptions();
		options.setProjectName(TEST_MODEL_PROJECT_NAME);
		options.setAllocationPath("models/CoronaWarnApp/default.usagemodel");
		options.setUsageModelPath("models/CoronaWarnApp/default.allocation");

		diagramGenerator = new StandaloneDiagramGenerator(options);
	}

	@Test
	void runGeneratorWithCoronaWarnApp() {
		PlantUMLDrawingStrategy drawer = new PlantUMLDrawingStrategy();
		DataFlowElementFactory creator = DataFlowElementFactory.getInstance();
		DataFlowGraphProcessor processor = new DataFlowGraphProcessor(creator);
		diagramGenerator.generateDataFlowDiagram(drawer, creator, processor);
	}
}
