package org.palladiosimulator.dataflow.diagramgenerator.model;

public class DataFlowNodePrimaryKey {
	public final String id;
	public final Boolean isCalling;
	public final String parameterString;

	public DataFlowNodePrimaryKey(String id, Boolean isCalling, String parameterString) {
		this.id = id;
		this.isCalling = isCalling;
		this.parameterString = parameterString;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this)
			return true;
		if (other instanceof DataFlowNodePrimaryKey key) {
			if (this.id.equals(key.id) && this.parameterString.equals(key.parameterString)) {
				if (this.isCalling == null && key.isCalling == null) {
					return true;
				} else if (this.isCalling.equals(key.isCalling)) {
					return true;
				}
			}
		}

		return false;
	}
}
