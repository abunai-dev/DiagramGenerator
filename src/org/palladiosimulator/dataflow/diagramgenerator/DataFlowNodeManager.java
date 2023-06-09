package org.palladiosimulator.dataflow.diagramgenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.palladiosimulator.dataflow.confidentiality.analysis.entity.sequence.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariable;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;

public class DataFlowNodeManager {
	public DataFlowNode createNewDataFlowNode(AbstractActionSequenceElement<?> actionSequenceElement,
			DataFlowElement dataFlowElement) {
		return new DataFlowNode(actionSequenceElement, dataFlowElement);
	}

	public void addLiteralsToNode(DataFlowNode dataFlowNode, List<DataFlowLiteral> literals) {
		literals.forEach(dataFlowNode::addLiteral);
	}

	public void addVariablesToNode(DataFlowNode dataFlowNode, List<DataFlowElementVariable> variables) {
		variables.forEach(dataFlowNode::addVariable);
	}

	public void connectNodes(DataFlowNode previousNode, DataFlowNode dataFlowNode) {
		if (previousNode != null) {
			previousNode.addChild(dataFlowNode);
			dataFlowNode.addParent(previousNode);
		}
	}

	public void addNodeToListIfNotExists(DataFlowNode dataFlowNode, List<DataFlowNode> dataFlowNodes) {
		if (!dataFlowNodes.contains(dataFlowNode)) {
			dataFlowNodes.add(dataFlowNode);
		}
	}

	public Map<DataFlowElement, DataFlowNode> createDataFlowElementNodeMap(List<DataFlowElement> dataFlowElements,
			List<DataFlowNode> dataFlowNodes) {
		Map<DataFlowElement, DataFlowNode> existingMap = new HashMap<>();

		for (DataFlowElement dfe : dataFlowElements) {
			DataFlowNode existingNode = findExistingNode(dfe, dataFlowNodes);
			if (existingNode == null) {
				existingNode = createNewDataFlowNode(null, dfe); // Pass null or appropriate argument for
																	// actionSequenceElement
				addNodeToListIfNotExists(existingNode, dataFlowNodes);
			}
			existingMap.put(dfe, existingNode);
		}

		return existingMap;
	}

	private DataFlowNode findExistingNode(DataFlowElement dataFlowElement, List<DataFlowNode> dataFlowNodes) {
		return dataFlowNodes.stream().filter(node -> node.getElement().equals(dataFlowElement)).findFirst()
				.orElse(null);
	}
}
