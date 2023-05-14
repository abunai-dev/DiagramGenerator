package org.palladiosimulator.dataflow.diagramgenerator;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.palladiosimulator.dataflow.confidentiality.analysis.StandalonePCMDataFlowConfidentialtyAnalysis;
import org.palladiosimulator.dataflow.confidentiality.analysis.testmodels.Activator;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowTree;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLEngine;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLUtility;
import org.palladiosimulator.pcm.seff.impl.AbstractActionImpl;
import org.palladiosimulator.pcm.seff.impl.StartActionImpl;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.ActionSequence;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.CallingSEFFActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.CallingUserActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.SEFFActionSequenceElement;

public class Main {
	public static void main(String[] args) {
		var projectName = "org.palladiosimulator.dataflow.confidentiality.analysis.testmodels";
		final var usageModelPath = Paths.get("models", "CoronaWarnApp", "default.usagemodel").toString();
		final var allocationPath = Paths.get("models", "CoronaWarnApp", "default.allocation").toString();
		StandalonePCMDataFlowConfidentialtyAnalysis analysis = new StandalonePCMDataFlowConfidentialtyAnalysis(
				projectName, Activator.class, usageModelPath, allocationPath);
		analysis.initalizeAnalysis();

		List<ActionSequence> actionSequences = analysis.findAllSequences();

		System.out.println("Initialization finished");

		List<DataflowElement> dataflowElements = new ArrayList<DataflowElement>();

		int idCounter = 0;

		for (ActionSequence actionSequence : actionSequences) {
			List<DataflowElement> actionSequenceDataflowElements = new ArrayList<DataflowElement>();

			DataflowElement currentElement = null;

			// ich muss in einer sequenz herausfinden, ob sachen öfter vorkommen und nicht
			// zwischen allen sequencen

			for (AbstractActionSequenceElement element : actionSequence.getElements()) {
				String id = EntityUtility.getEntityId(element);
				String name = EntityUtility.getEntityName(element);
				Boolean isCalling = EntityUtility.getIsCalling(element);
				String parameter = EntityUtility.getParameterString(element);

				DataflowElement newElement = new DataflowElement(id, idCounter, name, isCalling, null, parameter, 0,
						element.getClass().getName());

				String elementString = element.toString();

				if (newElement.getIsCalling() == null || newElement.getIsCalling() == true) {
					newElement.setParent(currentElement);
					currentElement = newElement;
					actionSequenceDataflowElements.add(newElement);
				} else if (newElement.getIsCalling() == false) { // Element is a returning element
					DataflowElement parent = currentElement.getParent();
					currentElement = parent;
				}

				idCounter++;
			}

			dataflowElements.addAll(actionSequenceDataflowElements);
		}
		System.out.println("ActionSequences comsumed");

		String source = "@startuml\n";

		for (DataflowElement element : dataflowElements) {
			String className = element.getClassName().substring(element.getClassName().lastIndexOf(".") + 1);

			if (!className.equals("CallingUserActionSequenceElement")
					&& !className.equals("CallingSEFFActionSequenceElement")) {

				source += "usecase u_" + element.getNumId() + "[\n";

				source += element.getName() + "\n";
				source += "----\n";
				source += "id: " + element.getId() + "\n";

				source += "]\n";

				DataflowElement iterator = element.getParent();
				while (iterator != null) {
					String iteratorClass = iterator.getClassName()
							.substring(element.getClassName().lastIndexOf(".") + 1);
					if (!iteratorClass.equals("CallingSEFFActionSequenceElement")
							&& !iteratorClass.equals("CallingUserActionSequenceElement")) {
						break;
					}
					iterator = iterator.getParent();
				}

				if (iterator != null) {
					if (element.getParameter() != "") {
						source += "u_" + iterator.getNumId() + " --> u_" + element.getNumId() + " : "
								+ element.getParameter() + "\n";
					} else {
						source += "u_" + iterator.getNumId() + " --> u_" + element.getNumId() + "\n";
					}
				}
			}
		}

		source += "@enduml";

		boolean isSaved = PlantUMLUtility.convertSourceToSVG(source, "dataflow_diagram.svg");
		if (!isSaved) {
			System.out.println("Error: saving failed!");
		}

		System.out.println("Done!");
	}
}
