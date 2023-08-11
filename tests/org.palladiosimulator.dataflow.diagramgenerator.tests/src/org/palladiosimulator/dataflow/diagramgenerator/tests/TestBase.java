package org.palladiosimulator.dataflow.diagramgenerator.tests;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.palladiosimulator.dataflow.confidentiality.analysis.characteristics.DataFlowVariable;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMDataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMDiagramGenerator;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMGraphProcessor;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLDrawingStrategy;
import org.palladiosimulator.dataflow.diagramgenerator.testmodels.Activator;
import org.palladiosimulator.dataflow.dictionary.characterized.DataDictionaryCharacterized.Literal;

public class TestBase {
	public static String PROJECT_NAME = "org.palladiosimulator.dataflow.diagramgenerator.testmodels";
	public static String USAGE_MODEL_PATH = "models/TravelPlannerNew/travelPlanner.usagemodel";
	public static String ALLOCATION_PATH = "models/TravelPlannerNew/travelPlanner.allocation";
	public static String CHARACTERISTICS_PATH = "models/TravelPlannerNew/travelPlanner.nodecharacteristics";

	@Test
	void runGeneratorWithModel() {
		GeneratorOptions options = GeneratorOptions.getInstance();
		options.setProjectName(PROJECT_NAME);
		options.setUsageModelPath(USAGE_MODEL_PATH);
		options.setAllocationPath(ALLOCATION_PATH);
		options.setCharacteristicsPath(CHARACTERISTICS_PATH);
		options.setDrawControlFlow(true);
		options.setDrawNodeCharacteristics(true);
		options.setDrawVariables(true);

		PCMDiagramGenerator diagramGenerator = new PCMDiagramGenerator(options, Activator.class);

		PlantUMLDrawingStrategy drawer = new PlantUMLDrawingStrategy();
		PCMDataFlowElementFactory creator = PCMDataFlowElementFactory.getInstance();

		Predicate<? super AbstractActionSequenceElement<?>> condition = node -> {
			List<String> assignedRoles = node.getNodeCharacteristicsWithName("AssignedRoles").stream()
					.map(it -> it.getName()).collect(Collectors.toList());
			Map<DataFlowVariable, List<Literal>> grantedRoles = node.getDataFlowCharacteristicsWithName("GrantedRoles");

			return grantedRoles.entrySet().stream().map(dfd -> {
				return !dfd.getValue().isEmpty() && dfd.getValue().stream().distinct()
						.filter(it -> assignedRoles.contains(it.getName())).collect(Collectors.toList()).isEmpty();
			}).anyMatch(Boolean::valueOf);
		};

		PCMGraphProcessor processor = new PCMGraphProcessor(creator, condition);

		diagramGenerator.generateDataFlowDiagram(drawer, processor);
	}
}
