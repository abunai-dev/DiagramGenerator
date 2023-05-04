package org.palladiosimulator.dataflow.diagramgenerator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataFlowTree {
	private Map<String, DataFlowNode> nodeMap;
	
	public DataFlowTree() {
		this.nodeMap = new HashMap<>();
	}
	
	public List<DataFlowNode> getRoots() {
		List<DataFlowNode> roots = new ArrayList<>();
		
		for (DataFlowNode node : nodeMap.values()) {
			if (node.getParent() == null) {
				roots.add(node);
			}
		}
		
		return roots;
	}
	
	public DataFlowNode getNode(String id) {
		return this.nodeMap.get(id);
	}
	
	public boolean insertNode(DataFlowNode node) {
		if (this.nodeMap.get(node.getId()) == null) {
			this.nodeMap.put(node.getId(), node);
			return true;
		}
		
		return false;
	}
	
	public Collection<DataFlowNode> getNodes() {
		return this.nodeMap.values();
	}
}
