package org.palladiosimulator.dataflow.diagramgenerator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.palladiosimulator.dataflow.diagramgenerator.DataFlowGraphProcessor;
import org.palladiosimulator.dataflow.diagramgenerator.GeneratorOptions;
import org.palladiosimulator.dataflow.diagramgenerator.StandaloneDiagramGenerator;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLDrawingStrategy;

public class UIDesigner {
	private static JTextField projectNameTextField;
	private static JTextField usageModelTextField;
	private static JTextField allocationTextField;

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

		// Create text fields for the arguments
		projectNameTextField = new JTextField("org.palladiosimulator.dataflow.confidentiality.analysis.testmodels", 20);
		usageModelTextField = new JTextField("models/CoronaWarnApp/default.usagemodel", 20);
		allocationTextField = new JTextField("models/CoronaWarnApp/default.allocation", 20);

		// Create a button to trigger the generation
		JButton button = new JButton("Generate Diagram");

		// Add an action listener to the button
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Handle button click event
				String projectName = projectNameTextField.getText();
				String usageModelPath = usageModelTextField.getText();
				String allocationPath = allocationTextField.getText();

				// Create the options object
				GeneratorOptions options = new GeneratorOptions();
				options.setProjectName(projectName);
				options.setUsageModelPath(usageModelPath);
				options.setAllocationPath(allocationPath);

				StandaloneDiagramGenerator diagramGenerator = new StandaloneDiagramGenerator(options);

				PlantUMLDrawingStrategy drawer = new PlantUMLDrawingStrategy();
				DataFlowElementFactory creator = DataFlowElementFactory.getInstance();
				DataFlowGraphProcessor processor = new DataFlowGraphProcessor(creator);
				diagramGenerator.generateDataFlowDiagram(drawer, creator, processor);
			}
		});

		// Add the components to the panel
		panel.add(projectNameLabel);
		panel.add(projectNameTextField);
		panel.add(usageModelLabel);
		panel.add(usageModelTextField);
		panel.add(allocationLabel);
		panel.add(allocationTextField);
		panel.add(button);

		// Add the panel to the frame
		frame.getContentPane().add(panel);

		// Set the frame size and make it visible
		frame.setSize(400, 200);
		frame.setVisible(true);
	}
}
