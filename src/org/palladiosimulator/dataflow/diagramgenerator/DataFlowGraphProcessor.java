package org.palladiosimulator.dataflow.diagramgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.palladiosimulator.dataflow.confidentiality.analysis.characteristics.CharacteristicValue;
import org.palladiosimulator.dataflow.confidentiality.analysis.characteristics.DataFlowVariable;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.ActionSequence;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariable;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.dictionary.characterized.DataDictionaryCharacterized.impl.EnumCharacteristicTypeImpl;
import org.palladiosimulator.dataflow.dictionary.characterized.DataDictionaryCharacterized.impl.LiteralImpl;

public class DataFlowGraphProcessor {
	private final DataFlowElementFactory elementCreator;

	public DataFlowGraphProcessor(DataFlowElementFactory elementCreator) {
		this.elementCreator = elementCreator;
	}

	public List<DataFlowNode> processActionSequences(List<ActionSequence> actionSequences) {
		List<DataFlowNode> dataFlowNodes = new ArrayList<>();

		for (ActionSequence actionSequence : actionSequences) {
			processActionSequence(actionSequence, dataFlowNodes);
		}

		return dataFlowNodes;
	}

	private void processActionSequence(ActionSequence actionSequence, List<DataFlowNode> dataFlowNodes) {
		DataFlowNode previousNode = null;

		for (AbstractActionSequenceElement<?> actionSequenceElement : actionSequence.getElements()) {
			List<DataFlowElementVariable> variables = createDataFlowElementVariables(actionSequenceElement);
			List<DataFlowLiteral> literals = createDataFlowLiterals(actionSequenceElement);
			List<DataFlowElement> dataFlowElements = elementCreator
					.createDataFlowElementsForActionSequenceElement(actionSequenceElement);
			Map<DataFlowElement, DataFlowNode> existingMap = createExistingMap(dataFlowElements, dataFlowNodes);

			for (Entry<DataFlowElement, DataFlowNode> dataFlowEntry : existingMap.entrySet()) {
				DataFlowNode dataFlowNode = dataFlowEntry.getValue();

				if (dataFlowNode == null) {
					dataFlowNode = new DataFlowNode(actionSequenceElement, dataFlowEntry.getKey());
					addNodeToListIfNotExists(dataFlowNode, dataFlowNodes);
				}

				connectNodes(previousNode, dataFlowNode);
				addVariablesToNode(dataFlowNode, variables);
				addLiteralsToNode(dataFlowNode, literals);
				previousNode = dataFlowNode;
			}
		}
	}

	private void addLiteralsToNode(DataFlowNode dataFlowNode, List<DataFlowLiteral> literals) {
		for (DataFlowLiteral literal : literals) {
			dataFlowNode.addLiteral(literal);
		}
	}

	private void addVariablesToNode(DataFlowNode dataFlowNode, List<DataFlowElementVariable> variables) {
		for (DataFlowElementVariable variable : variables) {
			dataFlowNode.addVariable(variable);
		}
	}

	private List<DataFlowElementVariable> createDataFlowElementVariables(
			AbstractActionSequenceElement<?> actionSequenceElement) {
		List<DataFlowElementVariable> variables = new ArrayList<>();

		for (DataFlowVariable variable : actionSequenceElement.getAllDataFlowVariables()) {
			DataFlowElementVariable elementVariable = null;
			boolean isNew = false;
			for (DataFlowElementVariable v : variables) {
				if (v.getName().equals(variable.variableName())) {
					elementVariable = v;
					break;
				}
			}
			if (elementVariable == null) {
				elementVariable = new DataFlowElementVariable(variable.variableName());
				isNew = true;
			}

			for (CharacteristicValue val : variable.getAllCharacteristics()) {
				LiteralImpl elementLiteral = (LiteralImpl) val.characteristicLiteral();
				EnumCharacteristicTypeImpl type = (EnumCharacteristicTypeImpl) val.characteristicType();

				elementVariable.addLiteral(new DataFlowLiteral(type.getId(), type.getName(), elementLiteral.getId(),
						elementLiteral.getName()));
			}

			if (isNew)
				variables.add(elementVariable);
		}

		return variables;
	}

	private List<DataFlowLiteral> createDataFlowLiterals(AbstractActionSequenceElement<?> actionSequenceElement) {
		List<DataFlowLiteral> literals = new ArrayList<>();

		for (CharacteristicValue val : actionSequenceElement.getAllNodeCharacteristics()) {
			LiteralImpl elementLiteral = (LiteralImpl) val.characteristicLiteral();
			EnumCharacteristicTypeImpl type = (EnumCharacteristicTypeImpl) val.characteristicType();

			literals.add(new DataFlowLiteral(type.getId(), type.getName(), elementLiteral.getId(),
					elementLiteral.getName()));
		}

		return literals;
	}

	private void connectNodes(DataFlowNode previousNode, DataFlowNode dataFlowNode) {
		if (previousNode != null) {
			previousNode.addChild(dataFlowNode);
			dataFlowNode.addParent(previousNode);
		}
	}

	private void addNodeToListIfNotExists(DataFlowNode dataFlowNode, List<DataFlowNode> dataFlowNodes) {
		if (!dataFlowNodes.contains(dataFlowNode)) {
			dataFlowNodes.add(dataFlowNode);
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
}
