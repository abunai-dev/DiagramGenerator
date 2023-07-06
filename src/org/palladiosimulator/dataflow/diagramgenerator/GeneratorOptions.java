package org.palladiosimulator.dataflow.diagramgenerator;

public class GeneratorOptions {
	private static GeneratorOptions instance;
	private String projectName;
	private String usageModelPath;
	private String allocationPath;
	private String characteristicsPath;
	private boolean drawNodeCharacteristics;
	private boolean drawVariables;
	
	private GeneratorOptions() {
		drawNodeCharacteristics = true;
		drawVariables = true;
	}
	
	public static synchronized GeneratorOptions getInstance() {
		if (instance == null) {
			instance = new GeneratorOptions();
		}
		return instance;
	}

	public String getCharacteristicsPath() {
		return characteristicsPath;
	}

	public void setCharacteristicsPath(String characteristicsPath) {
		this.characteristicsPath = characteristicsPath;
	}

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

	public boolean isDrawNodeCharacteristics() {
		return drawNodeCharacteristics;
	}

	public void setDrawNodeCharacteristics(boolean drawNodeCharacteristics) {
		this.drawNodeCharacteristics = drawNodeCharacteristics;
	}

	public boolean isDrawVariables() {
		return drawVariables;
	}

	public void setDrawVariables(boolean drawVariables) {
		this.drawVariables = drawVariables;
	}
}
