package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import java.util.Collection;

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
		this.createDrawings();

		this.source += "@enduml";

		System.out.println(this.source);

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
		Boolean isCalling = EntityUtility.getIsCalling(element);
		String parameter = EntityUtility.getParameterString(element);

		DataFlowNode node = new DataFlowNode(elementId, elementCounter, elementName, parameter, previousNode, element,
				isCalling);

		boolean isInserted = dataTree.insertNode(node);

		// first build tree structure
		if (!isInserted) {
			node = this.dataTree.getNode(node.getKey());
		} else {
			var i = 1;
		}

		if (previousNode != null) {
			previousNode.addChild(node);
		}

		elementCounter++;

		return node;
	}

	private void createDrawings() {
		this.drawNodes();
		System.out.println("Nodes drawn");
		this.drawCalling();
		System.out.println("Callings drawn");
		this.drawEdges();
		System.out.println("Edges drawn");
	}

	private void drawNodes() {
		for (DataFlowNode node : dataTree.getNodes()) {
			this.source += "rectangle \"" + node.getName() + "\" as " + node.getIdNumber() + "\n";
		}
	}

	private void drawCalling() {
		for (DataFlowNode node : dataTree.getNodes()) {
			if (node.isCalling() != null) {
				if (node.isCalling()) {
					this.source += "hexagon \"calling\" as calling_" + node.getIdNumber() + "\n";
					this.source += "calling_" + node.getIdNumber() + " ~~ " + node.getIdNumber() + "\n";
				} else {
					this.source += "hexagon \"receiving\" as receiving_" + node.getIdNumber() + "\n";
					this.source += "receiving_" + node.getIdNumber() + " ~~ " + node.getIdNumber() + "\n";
				}
			}
		}
	}

	private void drawEdges() {
		// find roots in tree
		for (DataFlowNode root : this.dataTree.getRoots()) {
			for (DataFlowNode child : root.getChildren()) {
				this.drawEdge(root, child);
			}
		}
	}

	private void drawEdge(DataFlowNode previous, DataFlowNode current) {
		System.out.println("Drawing edge from " + previous.getId() + ", " + previous.isCalling() + " to "
				+ current.getId() + ", " + current.isCalling());
		if (current == null)
			return;

		if (current.getKey().parameterString.length() > 0) {
			this.source += previous.getIdNumber() + " --> " + current.getIdNumber() + " : "
					+ current.getKey().parameterString + "\n";
		} else {
			this.source += previous.getIdNumber() + " --> " + current.getIdNumber() + "\n";
		}

		for (DataFlowNode child : current.getChildren()) {
			this.drawEdge(current, child);
		}
	}
}
