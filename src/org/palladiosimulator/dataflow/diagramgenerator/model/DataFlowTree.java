package org.palladiosimulator.dataflow.diagramgenerator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFlowTree {
	private Collection<DataFlowNode> nodes;

	public DataFlowTree() {
		this.nodes = new ArrayList<>();
	}

	public List<DataFlowNode> getRoots() {
		List<DataFlowNode> roots = new ArrayList<>();

		for (DataFlowNode node : nodes) {
			if (node.getParent() == null) {
				roots.add(node);
			}
		}

		return roots;
	}

	public DataFlowNode getNode(DataFlowNodePrimaryKey key) {
		for (DataFlowNode node : nodes) {
			if (node.getKey().equals(key)) {
				return node;
			}
		}

		return null;
	}

	public boolean insertNode(DataFlowNode node) {
		// if node already exists, do not insert
		if (this.getNode(node.getKey()) != null) {
			return false;
		} else {
			this.nodes.add(node);
			return true;
		}
	}

	public Collection<DataFlowNode> getNodes() {
		return this.nodes;
	}
}
