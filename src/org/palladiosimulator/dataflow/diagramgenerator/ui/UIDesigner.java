package org.palladiosimulator.dataflow.diagramgenerator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMDataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMDiagramGenerator;
import org.palladiosimulator.dataflow.diagramgenerator.pcm.PCMGraphProcessor;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLDrawingStrategy;

public class UIDesigner {
	private static JTextField projectNameTextField;
	private static JTextField usageModelTextField;
	private static JTextField allocationTextField;
	private static JTextField characteristicsTextField;

	public static void createUI() {
		// Create the UI frame
		JFrame frame = new JFrame("Data Flow Diagram Generator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create a panel to hold the components
		JPanel panel = new JPanel();

		// Create labels for the text fields
		JLabel projectNameLabel = new JLabel("Project Name:");
		JLabel usageModelLabel = new JLabel("Usage Model Path:");
		JLabel allocationLabel = new JLabel("Allocation Path:");
		JLabel characteristicsLabel = new JLabel("Characteristics Path:");

		// Create text fields for the arguments
		projectNameTextField = new JTextField("org.palladiosimulator.dataflow.confidentiality.analysis.testmodels", 40);
		usageModelTextField = new JTextField("models/BranchingOnlineShop/default.usagemodel", 40);
		allocationTextField = new JTextField("models/BranchingOnlineShop/default.allocation", 40);
		characteristicsTextField = new JTextField("models/BranchingOnlineShop/default.nodecharacteristics", 40);

		// Create checkboxes
		JCheckBox drawNodeCharacteristics = new JCheckBox("Draw Node Characteristics");
		JCheckBox drawVariables = new JCheckBox("Draw Variables");
		JCheckBox drawControlFlow = new JCheckBox("Draw Control Flow");

		// Create a button to trigger the generation
		JButton button = new JButton("Generate Diagram");

		// Add an action listener to the button
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Handle button click event
				String projectName = projectNameTextField.getText();
				String usageModelPath = usageModelTextField.getText();
				String allocationPath = allocationTextField.getText();
				String characteristicsPath = characteristicsTextField.getText();

				// Create the options object
				GeneratorOptions options = GeneratorOptions.getInstance();
				options.setProjectName(projectName);
				options.setUsageModelPath(usageModelPath);
				options.setAllocationPath(allocationPath);
				options.setCharacteristicsPath(characteristicsPath);

				// Perform actions based on checkbox selection
				options.setDrawNodeCharacteristics(drawNodeCharacteristics.isSelected());
				options.setDrawVariables(drawVariables.isSelected());
				options.setDrawControlFlow(drawControlFlow.isSelected());

				PCMDiagramGenerator diagramGenerator = new PCMDiagramGenerator(options);

				PlantUMLDrawingStrategy drawer = new PlantUMLDrawingStrategy();
				PCMDataFlowElementFactory creator = PCMDataFlowElementFactory.getInstance();
				PCMGraphProcessor processor = new PCMGraphProcessor(creator);
				diagramGenerator.generateDataFlowDiagram(drawer, processor);
			}
		});

		// Add the components to the panel
		panel.add(projectNameLabel);
		panel.add(projectNameTextField);
		panel.add(usageModelLabel);
		panel.add(usageModelTextField);
		panel.add(allocationLabel);
		panel.add(allocationTextField);
		panel.add(characteristicsLabel);
		panel.add(characteristicsTextField);
		panel.add(drawNodeCharacteristics);
		panel.add(drawVariables);
		panel.add(drawControlFlow);
		panel.add(button);

		// Add the panel to the frame
		frame.getContentPane().add(panel);

		// Set the frame size and make it visible
		frame.setSize(600, 200);
		frame.setVisible(true);
	}
}
