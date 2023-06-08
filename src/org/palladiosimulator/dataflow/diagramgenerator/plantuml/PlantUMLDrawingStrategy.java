package org.palladiosimulator.dataflow.diagramgenerator.plantuml;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElement;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowElementVariable;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowLiteral;
import org.palladiosimulator.dataflow.diagramgenerator.model.DataFlowNode;
import org.palladiosimulator.dataflow.diagramgenerator.model.DrawingStrategy;

import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

public class PlantUMLDrawingStrategy implements DrawingStrategy {
	private String source;

	public PlantUMLDrawingStrategy() {
		super();
		this.source = "";
	}

	@Override
	public void generate(List<DataFlowNode> dataFlowNodes) {
		this.initialize();
		// first, initialize all elements
		for (DataFlowNode node : dataFlowNodes) {
			PlantUMLDataFlowElementInitializerDrawingVisitor drawingVisitor = new PlantUMLDataFlowElementInitializerDrawingVisitor();
			PlantUMLDataFlowElementVariableInitializerVisitor variableVisitor = new PlantUMLDataFlowElementVariableInitializerVisitor();
			PlantUMLDataFlowLiteralInitializerDrawingVisitor literalVisitor = new PlantUMLDataFlowLiteralInitializerDrawingVisitor();
			DataFlowElement element = node.getElement();
			element.accept(drawingVisitor);
			this.addToSource(drawingVisitor.getDrawResult());

			for (DataFlowElementVariable variable : node.getVariables()) {
				variable.accept(variableVisitor);
				this.addToSource(variableVisitor.getDrawResult());

				for (DataFlowLiteral literal : variable.getLiterals()) {
					literal.accept(literalVisitor);
					this.addToSource(literalVisitor.getDrawResult());
				}
			}

			for (DataFlowLiteral literal : node.getLiterals()) {
				literal.accept(literalVisitor);
				this.addToSource(literalVisitor.getDrawResult());
			}
		}

		// second, draw the edges inbetween
		for (DataFlowNode node : dataFlowNodes) {
			PlantUMLDataFlowNodeDrawingVisitor drawingVisitor = new PlantUMLDataFlowNodeDrawingVisitor();
			PlantUMLDataFlowVariableEdgeDrawingVisitor variableEdgeVisitor = new PlantUMLDataFlowVariableEdgeDrawingVisitor();
			PlantUMLDataFlowLiteralEdgeDrawingVisitor literalEdgeVisitor = new PlantUMLDataFlowLiteralEdgeDrawingVisitor();

			node.accept(drawingVisitor);
			node.accept(variableEdgeVisitor);
			node.accept(literalEdgeVisitor);
			this.addToSource(drawingVisitor.getDrawResult());
			this.addToSource(variableEdgeVisitor.getDrawResult());
			this.addToSource(literalEdgeVisitor.getDrawResult());

			for (DataFlowElementVariable variable : node.getVariables()) {
				PlantUMLDataFlowVariableLiteralEdgeDrawingVisitor variableLiteralEdgeVisitor = new PlantUMLDataFlowVariableLiteralEdgeDrawingVisitor();
				variable.accept(variableLiteralEdgeVisitor);
				this.addToSource(variableLiteralEdgeVisitor.getDrawResult());
			}
		}
		this.finish();
	}

	private void addToSource(String addition) {
		this.source += addition;
	}

	@Override
	public boolean saveToDisk(String path) {
		SourceStringReader reader = new SourceStringReader(this.source);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		// Write the first image to "os"
		try {
			String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
			os.close();

			final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));

			BufferedWriter writer = new BufferedWriter(new FileWriter(path));
			writer.write(svg);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private void initialize() {
		this.source += "@startuml\n";
		this.source += "left to right direction\n";
	}

	private void finish() {
		this.source += "@enduml";
	}
}
