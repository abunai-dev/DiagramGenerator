package org.palladiosimulator.dataflow.diagramgenerator;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;

import org.palladiosimulator.dataflow.confidentiality.analysis.StandalonePCMDataFlowConfidentialtyAnalysis;
import org.palladiosimulator.dataflow.confidentiality.analysis.testmodels.Activator;
import org.palladiosimulator.pcm.seff.impl.AbstractActionImpl;
import org.palladiosimulator.pcm.seff.impl.StartActionImpl;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.ActionSequence;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.CallingSEFFActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.CallingUserActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.pcm.SEFFActionSequenceElement;

public class Main {
	public static void main(String[] args) {
		var projectName = "org.palladiosimulator.dataflow.confidentiality.analysis.testmodels";
		final var usageModelPath = Paths.get("models", "BranchingOnlineShop", "default.usagemodel").toString();
		final var allocationPath = Paths.get("models", "BranchingOnlineShop", "default.allocation").toString();
		StandalonePCMDataFlowConfidentialtyAnalysis analysis = new StandalonePCMDataFlowConfidentialtyAnalysis(
				projectName, Activator.class, usageModelPath, allocationPath);
		analysis.initalizeAnalysis();

		List<ActionSequence> actionSequences = analysis.findAllSequences();

		int counter = 0;

		for (ActionSequence actionSequence : actionSequences) {
			String source = "@startuml\n(*) --> ";
			String previousEntityName = null;

			List<AbstractActionSequenceElement<?>> elements = actionSequence.getElements();

			for (AbstractActionSequenceElement<?> element : elements) {
				String name = "";
				if (element instanceof CallingUserActionSequenceElement) {
					name = ((CallingUserActionSequenceElement) element).getElement().getEntityName();
				} else if (element instanceof SEFFActionSequenceElement) {
					name = ((AbstractActionImpl) ((SEFFActionSequenceElement) element).getElement()).getEntityName();
				} else {
					throw new Error();
				}

				if (previousEntityName == null) {
					source += "\"" + name + "\"\n";
				} else {
					source += "\"" + previousEntityName + "\" --> \"" + name + "\"\n";
				}

				previousEntityName = name;
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
				BufferedWriter writer = new BufferedWriter(new FileWriter("output/action-sequence_" + counter + ".svg"));
				writer.write(svg);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			counter++;
		}
	}
}
