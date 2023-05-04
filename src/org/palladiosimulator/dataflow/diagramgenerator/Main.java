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
		
		DiagramEngine engine = new PlantUMLEngine();
		
		engine.initialize();

		for (ActionSequence actionSequence : actionSequences) {
			engine.consumeActionSequence(actionSequence);
		}
		
		engine.finish();
	}
}
