package org.palladiosimulator.dataflow.diagramgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.ActionSequence;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;

public class DataFlowGraphProcessor {
	private DataFlowElementFactory elementCreator;

	public DataFlowGraphProcessor(DataFlowElementFactory elementCreator) {
		this.elementCreator = elementCreator;
	}

	/**
	 * Processes a list of action sequences and generates data flow nodes.
	 *
	 * @param actionSequences The list of action sequences to process.
	 * @return A list of data flow nodes generated from the action sequences.
	 */
	public List<DataFlowNode> processActionSequences(List<ActionSequence> actionSequences) {
		List<DataFlowNode> dataFlowNodes = new ArrayList<DataFlowNode>();

		for (ActionSequence actionSequence : actionSequences) {
			this.processActionSequence(actionSequence, elementCreator, dataFlowNodes);
		}

		return dataFlowNodes;
	}

	private void processActionSequence(ActionSequence actionSequence, DataFlowElementFactory elementCreator,
			List<DataFlowNode> dataFlowNodes) {
		DataFlowNode previousNode = null;

		for (AbstractActionSequenceElement actionSequenceElement : actionSequence.getElements()) {
			List<DataFlowElement> dataFlowElements = elementCreator.createDataFlowElements(actionSequenceElement);
			Map<DataFlowElement, DataFlowNode> existingMap = this.createExistingMap(dataFlowElements, dataFlowNodes);

			for (Entry<DataFlowElement, DataFlowNode> dataFlowEntry : existingMap.entrySet()) {
				DataFlowNode dataFlowNode = this.getDataFlowNode(dataFlowEntry, actionSequenceElement);
				if (previousNode != null) {
					previousNode.addChild(dataFlowNode);
					dataFlowNode.addParent(previousNode);
				}

				if (!dataFlowNodes.contains(dataFlowNode)) {
					dataFlowNodes.add(dataFlowNode);
				}
				previousNode = dataFlowNode;
			}
		}
	}

	private Map<DataFlowElement, DataFlowNode> createExistingMap(List<DataFlowElement> dataFlowElements,
			List<DataFlowNode> dataFlowNodes) {
		Map<DataFlowElement, DataFlowNode> existingMap = new HashMap<>();
		for (DataFlowElement dfe : dataFlowElements) {
			DataFlowNode existingNode = findExistingNode(dfe, dataFlowNodes);
			existingMap.put(dfe, existingNode);
		}
		return existingMap;
	}

	private DataFlowNode findExistingNode(DataFlowElement dataFlowElement, List<DataFlowNode> dataFlowNodes) {
		for (DataFlowNode node : dataFlowNodes) {
			if (node.getElement().equals(dataFlowElement)) {
				return node;
			}
		}
		return null;
	}

	private DataFlowNode getDataFlowNode(Entry<DataFlowElement, DataFlowNode> dataFlowEntry,
			AbstractActionSequenceElement actionSequenceElement) {
		DataFlowNode dataFlowNode = dataFlowEntry.getValue();
		if (dataFlowNode == null) {
			dataFlowNode = new DataFlowNode(actionSequenceElement, dataFlowEntry.getKey());
		}
		return dataFlowNode;
	}
}
