package org.palladiosimulator.dataflow.diagramgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.palladiosimulator.dataflow.confidentiality.analysis.characteristics.CharacteristicValue;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.ActionSequence;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.dictionary.characterized.DataDictionaryCharacterized.impl.EnumCharacteristicTypeImpl;
import org.palladiosimulator.dataflow.dictionary.characterized.DataDictionaryCharacterized.impl.EnumerationImpl;
import org.palladiosimulator.dataflow.dictionary.characterized.DataDictionaryCharacterized.impl.LiteralImpl;

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

		for (AbstractActionSequenceElement<?> actionSequenceElement : actionSequence.getElements()) {
			List<DataFlowLiteral> literals = new ArrayList<DataFlowLiteral>();
			List<CharacteristicValue> characteristics = actionSequenceElement.getAllNodeCharacteristics();

			for (CharacteristicValue val : characteristics) {
				LiteralImpl elementLiteral = (LiteralImpl) val.characteristicLiteral();
				EnumCharacteristicTypeImpl type = (EnumCharacteristicTypeImpl) val.characteristicType();

				String typeID = type.getId();
				String typeName = type.getName();
				String literalID = elementLiteral.getId();
				String literalName = elementLiteral.getName();

				literals.add(new DataFlowLiteral(typeID, typeName, literalID, literalName));
			}

			List<DataFlowElement> dataFlowElements = elementCreator.createDataFlowElements(actionSequenceElement);
			Map<DataFlowElement, DataFlowNode> existingMap = this.createExistingMap(dataFlowElements, dataFlowNodes);

			for (Entry<DataFlowElement, DataFlowNode> dataFlowEntry : existingMap.entrySet()) {
				DataFlowNode dataFlowNode = this.getDataFlowNode(dataFlowEntry, actionSequenceElement);
				if (previousNode != null) {
					previousNode.addChild(dataFlowNode);
					dataFlowNode.addParent(previousNode);
				}
				
				for (DataFlowLiteral literal : literals) {
					dataFlowNode.addLiteral(literal);
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
