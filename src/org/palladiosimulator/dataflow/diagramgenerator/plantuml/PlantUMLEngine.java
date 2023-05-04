package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.AbstractActionSequenceElement;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.ActionSequence;
import org.palladiosimulator.dataflow.diagramgenerator.DiagramEngine;
import org.palladiosimulator.dataflow.diagramgenerator.EntityUtility;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowTree;

public class PlantUMLEngine implements DiagramEngine {
	private String source;
	private DataFlowTree dataTree;
	private int elementCounter;

	@Override
	public void initialize() {
		this.source = "@startuml\n";
		this.dataTree = new DataFlowTree();
		this.elementCounter = 0;
	}

	@Override
	public void finish() {
		boolean isSaved = PlantUMLUtility.convertSourceToSVG(this.source, "dataflow_diagram.svg");
		if (!isSaved) {
			System.out.println("Error: saving failed!");			
		}
	}

	public void consumeActionSequence(ActionSequence actionSequence) {
		DataFlowNode previousNode = null;

		for (AbstractActionSequenceElement element : actionSequence.getElements()) {
			previousNode = this.consumeSequenceElement(previousNode, element);
		}
	}
	
	private DataFlowNode consumeSequenceElement(DataFlowNode previousNode, AbstractActionSequenceElement element) {
		String elementId = EntityUtility.getEntityId(element);
		String elementName = EntityUtility.getEntityName(element);

		DataFlowNode node = new DataFlowNode(elementId, elementCounter, elementName, previousNode, element);
		boolean isInserted = dataTree.insertNode(node);
		
		if (isInserted) {
			this.source += "rectangle \"" + node.getName() + "\" as " + node.getIdNumber() + "\n";
		}

		if (previousNode != null) {
			previousNode.addChild(node);
			this.source += previousNode.getIdNumber() + " --> " + node.getIdNumber() + "\n";
		}

		elementCounter++;
		
		return previousNode;
	}

}
