package org.palladiosimulator.dataflow.diagramgenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.palladiosimulator.dataflow.confidentiality.analysis.StandalonePCMDataFlowConfidentialtyAnalysis;
import org.palladiosimulator.dataflow.confidentiality.analysis.sequence.entity.ActionSequence;
import org.palladiosimulator.dataflow.confidentiality.analysis.testmodels.Activator;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementFactory;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.plantuml.PlantUMLDrawingStrategy;

public class Main {
	private static JTextField projectNameTextField;
	private static JTextField usageModelTextField;
	private static JTextField allocationTextField;

	public static void main(String[] args) {
		if (args.length > 0) {
			// If command-line arguments are provided, run the analysis directly
			CommandLineOptions options = CommandLineParser.parseCommandLineOptions(args);
			if (options != null) {
				runDataFlowDiagramGeneration(options);
			}
		} else {
			// Otherwise, create the UI
			createUI();
		}
	}

	private static void createUI() {
		// Create the UI frame
		JFrame frame = new JFrame("Data Flow Confidentiality Analysis");
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

		// Create a button to trigger the analysis
		JButton button = new JButton("Start Analysis");

		// Add an action listener to the button
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Handle button click event
				String projectName = projectNameTextField.getText();
				String usageModelPath = usageModelTextField.getText();
				String allocationPath = allocationTextField.getText();

				// Create the options object
				CommandLineOptions options = new CommandLineOptions();
				options.setProjectName(projectName);
				options.setUsageModelPath(usageModelPath);
				options.setAllocationPath(allocationPath);

				runDataFlowDiagramGeneration(options);
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

	private static void runDataFlowDiagramGeneration(CommandLineOptions options) {
		StandalonePCMDataFlowConfidentialtyAnalysis analysis = new StandalonePCMDataFlowConfidentialtyAnalysis(
				options.getProjectName(), Activator.class, options.getUsageModelPath(), options.getAllocationPath());
		analysis.initalizeAnalysis();

		List<ActionSequence> actionSequences = analysis.findAllSequences();

		System.out.println("Initialization finished!");

		DataFlowElementFactory elementCreator = DataFlowElementFactory.getInstance();
		DataFlowGraphProcessor graphProcessor = new DataFlowGraphProcessor(elementCreator);
		
		List<DataFlowNode> dataFlowNodes = graphProcessor.processActionSequences(actionSequences);
		
		System.out.println("Model translation finished!");
		
		PlantUMLDrawingStrategy drawer = new PlantUMLDrawingStrategy();
		
		drawer.generate(dataFlowNodes);
		drawer.saveToDisk("output/data-flow.svg");

		System.out.println("Done!");
	}
}
