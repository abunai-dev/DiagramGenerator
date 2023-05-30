package org.palladiosimulator.dataflow.diagramgenerator.model;

public class DataFlowNodeFactory {
	private static DataFlowNodeFactory instance;

	private DataFlowNodeFactory() {
	}

	public static synchronized DataFlowNodeFactory getInstance() {
		if (instance == null) {
			instance = new DataFlowNodeFactory();
		}

		return instance;
	}

	public DataFlowNode createDataFlowNode(DataFlowElement element) {

		return null;
	}
}
