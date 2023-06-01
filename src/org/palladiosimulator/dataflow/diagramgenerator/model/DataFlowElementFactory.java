package org.palladiosimulator.dataflow.diagramgenerator.model;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.dataflow.confidentiality.analysis.entity.pcm.seff.SEFFActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.pcm.user.CallingUserActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.diagramgenerator.EntityUtility;

public class DataFlowElementFactory {
	private static DataFlowElementFactory instance;

	private DataFlowElementFactory() {
	}

	public static synchronized DataFlowElementFactory getInstance() {
		if (instance == null) {
			instance = new DataFlowElementFactory();
		}

		return instance;
	}

	public List<DataFlowElement> createDataFlowElements(AbstractActionSequenceElement element) {
		List<DataFlowElement> dataFlowElements = new ArrayList<DataFlowElement>();

		String id = EntityUtility.getEntityId(element);
		String name = EntityUtility.getEntityName(element);
		Boolean isCalling = EntityUtility.getIsCalling(element);
		List<String> parameters = EntityUtility.getParameters(element);
		String actorName = EntityUtility.getActorName(element);

		// TODO: How to handle database elements

		if (element instanceof CallingUserActionSequenceElement cuase) {
			// Results in an actor if existing
			if (actorName != null) {
				TerminatorDataFlowElement terminator = new TerminatorDataFlowElement(actorName, true, actorName);
				dataFlowElements.add(terminator);
			}
		} else if (element instanceof SEFFActionSequenceElement sase) {
			// results in a ProcessDataFlowElement
			
			ProcessDataFlowElement process = new ProcessDataFlowElement(id, isCalling, name);
			process.addParameters(parameters);
			dataFlowElements.add(process);
		} else {
			throw new Error("Not implemented");
		}

		return dataFlowElements;
	}
}
