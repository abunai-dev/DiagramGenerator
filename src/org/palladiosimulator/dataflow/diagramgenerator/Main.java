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

import javax.xml.crypto.Data;

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
			List<DataflowElement> currentElement2 = new ArrayList<DataflowElement>();
			DataflowElement currentElement = null;

			for (AbstractActionSequenceElement element : actionSequence.getElements()) {
				String id = EntityUtility.getEntityId(element);
				String name = EntityUtility.getEntityName(element);
				Boolean isCalling = EntityUtility.getIsCalling(element);
				String parameter = EntityUtility.getParameterString(element);

				// find element with same id, name, isCalling and parameter in dataflowElements
				DataflowElement foundElement = null;
				for (DataflowElement dataflowElement : dataflowElements) {
					if (dataflowElement.getId().equals(id) && dataflowElement.getName().equals(name)
							&& dataflowElement.getIsCalling() == isCalling
							&& dataflowElement.getParameter().equals(parameter)) {
						foundElement = dataflowElement;
						break;
					}
				}

				// if foundElement != null, check if the foundElement is a distinct parent of
				// the currentElement
				boolean isDistinctParent = false;
				if (foundElement != null) {
					List<DataflowElement> parents = new ArrayList<DataflowElement>();
					if (currentElement2.size() > 0)
						for (DataflowElement currentElement3 : currentElement2) {
							parents.addAll(currentElement3.getParents());
						}
					if (!isDistinctParent) {
						while (parents.size() > 0) {
							for (DataflowElement parent : parents) {
								if (parent.equals(foundElement)) {
									isDistinctParent = true;
									break;
								}
							}
							List<DataflowElement> newParents = new ArrayList<DataflowElement>();
							for (DataflowElement parent : parents) {
								newParents.addAll(parent.getParents());
							}
							parents = newParents;
						}
					}
				}

				if (foundElement == null || isDistinctParent) {
					DataflowElement newElement = new DataflowElement(id, idCounter, name, isCalling, parameter, 0,
							element.getClass().getName().substring(element.getClass().getName().lastIndexOf(".") + 1));

					String elementString = element.toString();

					if (newElement.getIsCalling() == null || newElement.getIsCalling() == true) {
						if (currentElement2.size() > 0)
							for (DataflowElement currentElement3 : currentElement2) {
								newElement.addParent(currentElement3);
							}
						currentElement2.clear();
						currentElement2.add(newElement);
						dataflowElements.add(newElement);
					} else if (newElement.getIsCalling() == false) { // Element is a returning element
						List<DataflowElement> parents = new ArrayList<DataflowElement>();
						currentElement2.clear();
						for (DataflowElement parent : parents) {
							currentElement2.add(parent);
						}
					}

					idCounter++;
				} else {
					for (DataflowElement currentElement3 : currentElement2) {
						foundElement.addParent(currentElement3);
					}
					currentElement2.clear();
					currentElement2.add(foundElement);
				}
			}
		}
		System.out.println("ActionSequences comsumed");

		String source = "@startuml\n";

		// ein mal zeichnen für SEFFActionSequenceElements: durchgezogene linien
		List<DataflowElement> seffActionSequenceElements = new ArrayList<DataflowElement>();
		for (DataflowElement element : dataflowElements) {
			if (element.getClassName().equals("SEFFActionSequenceElement")) {
				seffActionSequenceElements.add(element);
			}
		}

		for (DataflowElement element : seffActionSequenceElements) {
			source += "usecase u_" + element.getNumId() + "[\n";

			source += element.getName() + "\n";
			source += "----\n";
			source += element.getClassName() + "\n";
			source += "....\n";
			source += "id: " + element.getId() + "\n";

			source += "]\n";

			List<DataflowElement> iterators = element.getParents();
			while (iterators.size() > 0) {
				boolean shouldBreak = true;
				for (DataflowElement iterator : iterators) {
					String iteratorClass = iterator.getClassName()
							.substring(element.getClassName().lastIndexOf(".") + 1);
					if (iteratorClass.equals("CallingSEFFActionSequenceElement")
							|| iteratorClass.equals("CallingUserActionSequenceElement")) {
						shouldBreak = false;
						break;
					}
				}
				if (shouldBreak) {
					break;
				} else {
					List<DataflowElement> parents = new ArrayList<DataflowElement>();
					for (DataflowElement iterator : iterators) {
						parents.addAll(iterator.getParents());
					}
					iterators = parents;
				}
			}

			if (iterators.size() > 0) {
				for (DataflowElement prevElement : iterators) {
					if (element.getParameter() != "") {
						source += "u_" + prevElement.getNumId() + " --> u_" + element.getNumId() + " : "
								+ element.getParameter() + "\n";
					} else {
						source += "u_" + prevElement.getNumId() + " --> u_" + element.getNumId() + "\n";
					}
				}
			}
		}

		// ein mal zeichnen für den rest: gepunktete linien
		for (DataflowElement element : dataflowElements) {

			if (!element.getClassName().equals("SEFFActionSequenceElement")) {
				source += "rectangle u_" + element.getNumId() + "[\n";

				source += element.getName() + "\n";
				source += "----\n";
				source += element.getClassName() + "\n";
				source += "....\n";
				source += "id: " + element.getId() + "\n";

				source += "]\n";
			}

			List<DataflowElement> parents = element.getParents();

			for (DataflowElement parent : parents) {
				if (parent != null) {
					if (!(element.getClassName().equals("SEFFActionSequenceElement")
							&& parent.getClassName().equals("SEFFActionSequenceElement"))) {
						if (element.getParameter() != "") {
							source += "u_" + parent.getNumId() + " ..> u_" + element.getNumId() + " : "
									+ element.getParameter() + "\n";
						} else {
							source += "u_" + parent.getNumId() + " ..> u_" + element.getNumId() + "\n";
						}
					}
				}
			}
		}

		source += "@enduml";

		System.out.println(source);

		boolean isSaved = PlantUMLUtility.convertSourceToSVG(source, "dataflow_diagram.svg");
		if (!isSaved) {
			System.out.println("Error: saving failed!");
		}

		System.out.println("Done!");
	}
}
