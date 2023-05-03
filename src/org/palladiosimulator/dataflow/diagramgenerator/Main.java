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

		Map<String, DataFlowNode> nodeMap = new HashMap<>();

		int elementCounter = 0;

		for (ActionSequence actionSequence : actionSequences) {
			DataFlowNode previousNode = null;

			for (AbstractActionSequenceElement element : actionSequence.getElements()) {
				String elementId = EntityUtility.getEntityId(element);
				String elementName = EntityUtility.getEntityName(element);
				DataFlowNode node = nodeMap.get(elementId);

				if (node == null) {
					node = nodeMap.get(elementId);
					if (node == null) {
						node = new DataFlowNode(elementId, elementCounter, elementName, previousNode, element);
						nodeMap.put(elementId, node);
					}
				}

				if (previousNode != null) {
					previousNode.addChild(node);
				}

				previousNode = node;

				elementCounter++;
			}
		}

		String source = "@startuml\n";

		List<DataFlowNode> roots = new ArrayList<>();

		for (DataFlowNode node : nodeMap.values()) {
			if (node.getParent() == null) {
				roots.add(node);
			}
			source += "rectangle \"" + node.getName() + "\" as " + node.getIdNumber() + "\n";
		}

		for (DataFlowNode node : nodeMap.values()) {
			List<DataFlowNode> children = node.getChildren();

			for (DataFlowNode child : children) {
				source += node.getIdNumber() + " --> " + child.getIdNumber() + "\n";
			}
		}

		source += "@enduml";

		SourceStringReader reader = new SourceStringReader(source);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		// Write the first image to "os"
		try {
			String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// The XML is stored into svg
		final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));

		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("output/data-flow.svg"));
			writer.write(svg);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(source);
	}
}
