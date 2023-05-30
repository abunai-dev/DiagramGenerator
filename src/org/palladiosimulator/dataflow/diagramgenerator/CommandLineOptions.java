package org.palladiosimulator.dataflow.diagramgenerator;

public class CommandLineOptions {
	private String projectName;
	private String usageModelPath;
	private String allocationPath;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUsageModelPath() {
		return usageModelPath;
	}

	public void setUsageModelPath(String usageModelPath) {
		this.usageModelPath = usageModelPath;
	}

	public String getAllocationPath() {
		return allocationPath;
	}

	public void setAllocationPath(String allocationPath) {
		this.allocationPath = allocationPath;
	}

	public boolean isValid() {
		return projectName != null && usageModelPath != null && allocationPath != null;
	}
}
