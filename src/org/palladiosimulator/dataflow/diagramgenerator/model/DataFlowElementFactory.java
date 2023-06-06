package org.palladiosimulator.dataflow.diagramgenerator.model;

import java.util.ArrayList;
import java.util.List;

import org.palladiosimulator.dataflow.confidentiality.analysis.entity.pcm.seff.SEFFActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.pcm.user.CallingUserActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.diagramgenerator.EntityUtility;

/**
 * The DataFlowElementFactory class is responsible for creating data flow
 * elements based on the given action sequence elements.
 */
public class DataFlowElementFactory {
	private static DataFlowElementFactory instance;

	private DataFlowElementFactory() {
	}

	/**
	 * Returns the singleton instance of the DataFlowElementFactory.
	 * 
	 * @return The singleton instance of the factory.
	 */
	public static synchronized DataFlowElementFactory getInstance() {
		if (instance == null) {
			instance = new DataFlowElementFactory();
		}
		return instance;
	}

	/**
	 * Creates data flow elements for the given action sequence element.
	 * 
	 * @param element The action sequence element to create data flow elements for.
	 * @return The list of created data flow elements.
	 * @throws UnsupportedOperationException if the element type is not supported.
	 */
	public List<DataFlowElement> createDataFlowElementsForActionSequenceElement(
			AbstractActionSequenceElement<?> element) {
		List<DataFlowElement> dataFlowElements = new ArrayList<>();

		String id = EntityUtility.getEntityId(element);
		String name = EntityUtility.getEntityName(element);
		Boolean isCalling = EntityUtility.getIsCalling(element);
		List<String> parameters = EntityUtility.getParameters(element);
		String actorName = EntityUtility.getActorName(element);

		if (element instanceof CallingUserActionSequenceElement) {
			createExternalEntityDataFlowElement(dataFlowElements, actorName);
		} else if (element instanceof SEFFActionSequenceElement) {
			createProcessDataFlowElement(dataFlowElements, id, isCalling, name, parameters);
		} else {
			throw new UnsupportedOperationException("Element type not supported");
		}

		return dataFlowElements;
	}

	private void createExternalEntityDataFlowElement(List<DataFlowElement> dataFlowElements, String actorName) {
		if (actorName != null) {
			ExternalEntityDataFlowElement terminator = new ExternalEntityDataFlowElement(actorName, true, actorName);
			dataFlowElements.add(terminator);
		}
	}

	private void createProcessDataFlowElement(List<DataFlowElement> dataFlowElements, String id, Boolean isCalling,
			String name, List<String> parameters) {
		ProcessDataFlowElement process = new ProcessDataFlowElement(id, isCalling, name);
		process.addParameters(parameters);
		dataFlowElements.add(process);
	}
}
